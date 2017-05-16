# Data Collector
To be able to use the project, it is needed to collect some (a lot) of data for the system to do data mining with, so that it is possible to show the information's about a person that you wish.

## Intro
The data collector i writen in python 3.5 and is using 'Scrapy' for the collecting data from the social medias. 

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
<strong> Twitter

``` python twitter/main.py```