# SocialMediaInformer

This is a school project, made at BJTU.

The goal was to make a webscraper that could harvest information from social media, and then apply data mining to gather information from the profiles.

## Project Requirements
* We want to build up a system, taht can dynamically construct the user profile.
* Based on the social media data, from Weibo or Twitters, together with other information such as information from forums.
	* Chinese students work with the Weibo/Zhihu data.
	* Foreign students would work with the Twitter/Facebook data.
* To build Personas via Bigdata analysis based on huge data mount.
 

## Project consists of
* A social media crawler
* Spark application
* Client Tool (Web App)
* (Some python scrripts)

### Social Media Crawler
For the project, it is needed to have some data for mining. The crawler will gather information from:
* Twitter
* Wikipedia
* IMDB

 

## How to use
1. Go to the 'DataCollector', and follow its instructions to gather the informations for the system.
2. The data structure of the files from the data collection, needs to be changed abit - So put the script files into the same folder as the data files, and run them one-by-one.
3. For this project, we have used MySQL-server. The entire database structure can be found in the sql file.
4. The Spark application ('SparkyThings') ******
5. To run the Web-interface to display the content of what have been gathered, follow the instructions in the Client-UI.

