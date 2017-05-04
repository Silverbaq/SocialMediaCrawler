# Flow
1. Get "my" profile (name, id, tweets)
2. Get list of followers (username / id) - Add to "scrape" queue.
3. Get list of following (username / id) - Add to "scrape" queue.
4. Pull profile username/id from "scrape" queue
    * Check if user has been scraped, if not start from 1. with current user
5. If "scrape" queue is empty = finished
