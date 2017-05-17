# Data Collector
To be able to use the project, it is needed to collect some (a lot) of data for the system to do data mining with, so that it is possible to show the information's about a person that you wish.

## Intro
The data collector(s) are written in python. Then are collecting data from three diffirent sources:

* Twitter
* Wikipedia
* IMDB

To begin with, it is needed to have a lot of data from Twitter, since the two others scrappers are based on the data and the people that have been found at Twitter. 

## Setup
Make sure you have the needed applications:

<strong> Ubuntu/Debian </strong>

    sudo apt-get update
    sudo apt-get install python3 python-pip virtualenv

1. Make a virtual environment <br>
``` virtualenv -p python3 venv ```

2. Activate the virtual environment <br>
``` source venv/bin/activate ```

3. Install dependencies <br> 
``` pip install -r requirements.txt ```

## Run
Both the Twitter and the DataCollector has it's own README that will describe how to run the script.
