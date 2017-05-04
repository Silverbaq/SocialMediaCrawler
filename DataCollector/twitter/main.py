import queue
import tweepy
import keys_secrets

auth = tweepy.OAuthHandler(keys_secrets.CONSUMER_KEY, keys_secrets.CONSUMER_SECRET)
auth.set_access_token(keys_secrets.ACCESS_KEY, keys_secrets.ACCESS_SECRET)
api = tweepy.API(auth, wait_on_rate_limit=True)
me = api.me()

scrape_queue = queue.Queue()
scraped_profiles = {}
scraped_tweets = {}


def write_tweet(line):
    with open("tweets.txt", "a") as myfile:
        myfile.write(line+'\n')


def write_relation(line):
    with open("relations.txt", "a") as myfile:
        myfile.write(line+'\n')


def write_user(line):
    with open("profiles.txt", "a") as myfile:
        myfile.write(line+'\n')


class MyScraper(object):
    def __init__(self, id):
        self.id = id

    def scrape(self):
        user = api.get_user(id)
        name = user.name
        profile_image = user.profile_image_url
        screen_name = user.screen_name
        line = "{}      {}     {}        {}".format(self.id, name, screen_name, profile_image)
        write_user(line)

    def get_tweets(self):
        tweets = api.user_timeline(id=self.id, count=200)
        for tweet in tweets:
            if tweet.id not in scraped_tweets:
                id = tweet.id
                text = tweet.text
                date = tweet.created_at
                tweeter_id = tweet.user.id

                scraped_tweets[id] = True
                line = "{}      {}      {}      {}".format(tweeter_id, id, date, text)
                write_tweet(line)

    def get_followers(self):
        followers = api.followers_ids(self.id)
        for follower in followers:
            line = "{}      {}".format(follower, self.id)
            write_relation(line)
            if follower not in scraped_profiles:
                scrape_queue.put(follower)

    def get_following(self):
        following = api.friends_ids(self.id)
        for follow in following:
            line = "{}      {}".format(self.id, follow)
            write_relation(line)
            if follow not in scraped_profiles:
                scrape_queue.put(follow)


if __name__ == '__main__':
    scrape_queue.put(me.id)
    while not scrape_queue.empty():
        id = scrape_queue.get()
        scraper = MyScraper(id)
        scraper.scrape()
        scraper.get_tweets()
        scraper.get_followers()
        scraper.get_following()
        scraped_profiles[id] = True
