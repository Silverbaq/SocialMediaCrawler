## Python 2.7

### Setup
* Make virtualenv 
```
sudo apt-get install python-dev  \
     build-essential libssl-dev libffi-dev \
     libxml2-dev libxslt1-dev zlib1g-dev \
     python-pip

```


```  
virtualenv -p /usr/bin/python2.7 venv
source venv/bin/activate        
```
    
* Install dependencies
``` 
pip install -r requirements.txt
```


## Wiki Collector
### Run
Copy 'profiles.txt' from the Twitter collector into the input folder.

Or make a file named 'profiles.txt', and let the content have an ID at index 0 and profile name at index 1.  

Execute script with:
``` 
python wiki_collector.py
```
### Output
* ['id'] = Twitter id
* ['name'] = Person Name
* ['content'] = Content about person from Wikipedia
* ['images'] = a list of image urls
* ['refs'] = A list of references for more information
* ['wiki_url'] = A URL for a the Wikipedia page
* ['birthday'] = When was the person born (YYYY-MM-dd)
* ['death'] = If the person is dead (YYYY-MM-dd) - else empty
* ['occupation'] = a list of occupations
* ['spouse'] = The persons status as an array. index 0 is status, index 1 is with whom
* ['children'] = Amount of children
* ['mother'] = Name mother 
* ['father'] = Name of father

## IMDB Collector
### Run
Copy 'profiles.txt' from the Twitter collector into the input folder.

Or make a file named 'profiles.txt', and let the content have an ID at index 0 and profile name at index 1.  

Execute script with:
``` 
python imdb_collector.py
```
### Output
Two files in output folder:

* imdb_basic_output.txt
```
{
    'id': String, <--- (twitter_id)
    'name': String,
    'occupation': List[String],
    'birthday': String,
    'death': String,
    'spouse': List[String],
    'children': Integer
}
```
* imdb_content_output.txt
```
{
    'id': String, <--- (twitter_id)
    'content': String
}  
```