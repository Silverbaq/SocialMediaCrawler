from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from model import *

class Persona(object):
    def __init__(self, profile, content, occupation, references, spouse, social_network_status, pictures, tweets, followers,following, profile_image):
        self.profile = profile
        self.content = content
        self.occupations = occupation
        self.references = references
        self.spouse = spouse
        self.social_network_status = social_network_status
        self.pictures = pictures
        self.tweets = tweets
        self.followers = followers
        self.following = following
        self.profile_image = profile_image

def search_for_persona(name):
    engine = create_engine('mysql://root:123456@127.0.0.1/Persona')
    engine.connect()
    Session = sessionmaker(bind=engine)
    session = Session()
    profile = session.query(Profile).filter_by(Name=name).all()

    personas = []
    for p in profile:
        profile_image = session.query(ProfileImage).filter_by(Profile=p).first()
        personas.append({'profile': p, 'image': profile_image})
    session.close()
    engine.dispose()
    return personas

def get_persona(id):
    engine = create_engine('mysql://root:123456@127.0.0.1/Persona')
    engine.connect()
    Session = sessionmaker(bind=engine)
    session = Session()

    profile = session.query(Profile).filter_by(ProfileID=id).first()
    if profile is None:
        return None
    content_info = session.query(InfoContent).filter_by(ProfileID=profile.ProfileID).first()
    #make text displayable
    if content_info is not None:
        content_info.ContentInfo = code_or_remove(content_info.ContentInfo)
    else:
        content_info = ''

    profile_occupation = session.query(ProfileOccupation).filter_by(Profile=profile).all()
    reference = session.query(Reference).filter_by(Profile=profile).all()
    spouce = session.query(Spouce).filter_by(Profile=profile).all()
    social_network_status = session.query(SocialNetworkStatus).filter_by(Profile=profile).first()
    pictures = session.query(Picture).filter_by(Profile=profile).all()

    profile_image = session.query(ProfileImage).filter_by(Profile=profile).first()

    followers = 0 #session.query(Follower).filter_by(FollowingID=profile.ProfileID).count()
    following = 0 #session.query(Follower).filter_by(FollowerID=profile.ProfileID).count()
    tweets = [] #session.query(Tweet).filter_by(ProfileID=profile.ProfileID).all()


    session.close()
    engine.dispose()

    return Persona(profile, content_info, profile_occupation, reference, spouce, social_network_status, pictures, tweets, followers,following, profile_image)

def get_tweets(id):
    engine = create_engine('mysql://root:123456@127.0.0.1/Persona')
    engine.connect()
    Session = sessionmaker(bind=engine)
    session = Session()

    tweets = session.query(Tweet).filter_by(ProfileID=id).all()

    session.close()
    engine.dispose()

    result = []
    for t in tweets:
        msg = code_or_remove(t.TweetContent)
        t.TweetContent = msg
        result.append(t)

    return result

def get_top_pagerankers():
    engine = create_engine('mysql://root:123456@127.0.0.1/Persona')
    engine.connect()
    Session = sessionmaker(bind=engine)
    session = Session()

    socials= session.query(SocialNetworkStatus).all()

    sorted_socials = sorted(socials, key=lambda r:r.PageRank, reverse=True)[:100]

    for n in sorted_socials:
        n.Profile.Name = code_or_remove(n.Profile.Name)

    return sorted_socials


def code_or_remove(text):
    result = ''
    for t in text:
        try:
            result += t.decode('utf-8', 'ignore')
        except:
            pass

    return result