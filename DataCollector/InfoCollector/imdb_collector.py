from imdbpie import Imdb
import json
import requests
import lxml.html
import re


def imdb_content(twitter_id, twitter_name):
    imdb = Imdb()
    # imdb = Imdb(anonymize=True)  # to proxy requests
    id = imdb.search_for_person(twitter_name)[0]['imdb_id']

    hxs = lxml.html.document_fromstring(requests.get("http://www.imdb.com/name/" + str(id)).content)

    name = ''
    try:
        name = hxs.xpath('//*[@id="overview-top"]/h1/span/text()')[0].strip()
    except:
        #try:
        #    name = hxs.xpath('//*[@id="overview-top"]/div[1]/div/h1/span/text()')[0].strip()
        #except:
        #    pass
        pass

    if name.lower() == twitter_name.lower():
        try:
            occupation = [o.strip() for o in hxs.xpath('//*[@id="name-job-categories"]/a[*]/span/text()')]
        except:
            occupation = ''

        try:
            birthday = hxs.xpath('//*[@id="name-born-info"]/time')[0].attrib['datetime']
        except:
            birthday = ''

        try:
            death = hxs.xpath('//*[@id="name-death-info"]/time')[0].attrib['datetime']
        except:
            death = ""

        hxs_bio = lxml.html.document_fromstring(requests.get("http://www.imdb.com/name/" + id + "/bio").content)

        try:
            content = ''.join(hxs_bio.xpath('//*[@id="bio_content"]/div[2]/p[1]/text()'))
        except:
            content = ''

        try:
            spouse = hxs_bio.xpath('//*[@id="tableSpouses"]//tr/td[1]/a/text()')
        except:
            spouse = ''

        try:
            children_content = hxs_bio.xpath('//*[@id="tableSpouses"]//tr/td[2]/text()')
            children = 0
            for line in children_content:
                l = line.encode('UTF-8').replace('(', '( ').strip()
                pattern = '(.*) (.*) child'
                compiled = re.compile(pattern)
                m = compiled.search(l)
                if m is not None:
                    children += int(m.group(2))
        except:
            children = ''

        basic_info = {
            'id': twitter_id,
            'name': name,
            'occupation': occupation,
            'birthday': birthday,
            'death': death,
            'spouse': spouse,
            'children': str(children)
        }

        content_info = {
            'id': twitter_id,
            'content': content.strip()
        }

        write_imdb_data(json.dumps(basic_info), json.dumps(content_info))


def write_imdb_data(basic_info, content_info):
    f = open("output/imdb_basic_output.txt", "a+")
    f.write(basic_info + '\n')

    f = open("output/imdb_content_output.txt", "a+")
    f.write(content_info + '\n')


if __name__ == "__main__":
    imdb_content(0, 'NICKI MINAJ')

    for line in open('input/profiles.txt', 'r'):
        l = line.split('    ')
        try:
            imdb_content(l[0].strip(), l[1].strip())
            print 'Done with: {}'.format(l[1])
        except Exception as ex:
            print '{} failed: {}'.format(l[1], ex)
    print('Finished!')
