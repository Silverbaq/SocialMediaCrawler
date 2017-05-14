import wikipedia
import json
import requests
import mwparserfromhell


def wiki_content(name, id):
    s_result = wikipedia.search(name)
    if len(s_result) > 0 and s_result[0].lower() == name.lower():
        page = wikipedia.page(s_result[0])
        page_id = str(page.pageid)
        name = str(page.title)

        result = wiki_web_query(name, page_id)
        result['id'] = id
        result['name'] = name
        result['content'] = page.content
        result['images'] = page.images
        result['refs'] = page.references
        result['wiki_url'] = page.url

        write_wiki_data(json.dumps(result))


def wiki_web_query(name, page_id):
    request = {'action': 'query', 'format': 'json', 'prop': 'revisions', 'rvprop': 'content', 'titles': name}
    req = request.copy()

    # Call API
    result = requests.get('https://en.wikipedia.org/w/api.php', params=req).json()
    data = result['query']['pages'][page_id]['revisions'][0]['*']

    birthday = ''
    death = ''
    occupation = ''
    spouse = ''
    children = ''
    mother = ''
    father = ''

    # Parsing data
    wikicode = mwparserfromhell.parse(data)
    for template in wikicode.filter_templates():
        if template.name.matches("Infobox person") and not template.has("date"):
            for t in template.params:
                if t.name.matches("birth_date"):
                    b_day = mwparserfromhell.parse(t.value).get(1)
                    tmp = b_day.params
                    if tmp[0].name.nodes[0] == 'mf':
                        birthday = '{}-{}-{}'.format(tmp[1], tmp[2], tmp[3])
                    elif tmp[0].name.nodes[0] == 'df':
                        birthday = '{}-{}-{}'.format(tmp[1], tmp[2], tmp[3])
                    else:
                        birthday = '{}-{}-{}'.format(tmp[0], tmp[1], tmp[2])
                elif t.name.matches("death_date"):
                    d_day = mwparserfromhell.parse(t.value).get(1)
                    tmp = d_day.params
                    death = '{}-{}-{}'.format(tmp[0], tmp[1], tmp[2])
                elif t.name.matches("occupation"):
                    occupation = [str(s.replace('\n', '').strip()) for s in t.value.nodes[0].value.split(',')]
                elif t.name.matches("spouse"):
                    status = str(t.value.nodes[1].name)
                    s_name = str(t.value.nodes[1].params[0].value)
                    spouse = [status, s_name]
                elif t.name.matches("children"):
                    children = str(t.value.nodes[0].split(',')[0])
                elif t.name.matches("mother"):
                    mother = str(t.value.nodes[1].text)
                elif t.name.matches("father"):
                    father = str(t.value.nodes[1].text)

    return {
        "birthday": birthday,
        "death": death,
        "occupation": occupation,
        "spouse": spouse,
        "children": children,
        "mother": mother,
        "father": father
    }


def write_wiki_data(data):
    f = open("output/wiki_output.txt", "a+")
    f.write(data + '\n')


if __name__ == "__main__":
    wiki_content('Steve Wozniak', '22938914')

    '''
    for line in open('input/profiles.txt', 'r'):
        l = line.split('    ')
        try:
            wiki_content(l[1].strip(), l[0].strip())
            print 'Done with: {}'.format(l[1])
        except Exception as ex:
            print '{} failed: {}'.format(l[1], ex)
    '''