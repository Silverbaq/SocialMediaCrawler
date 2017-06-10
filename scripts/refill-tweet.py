import json

input = 'tweets.txt'
output = 'tweets-re.txt'

failed = 0
total = 0

with open(input, "r") as ins:
    f = open(output, 'a')

    myLine = ''

    for line in ins:
        l = line.split('      ')
        if len(l) > 2:
            if myLine != '':
                res = myLine.split('      ')
                try:
                    total += 1
                    data = {'twitterId': res[0],
                            'id': res[1],
                            'date': res[2],
                            'message': res[3]
                            }
                    f.write(json.dumps(data) + '\n')
                except Exception as ex:
                    failed += 1
                    print(ex.message)
                myLine = ''
        myLine += line

    if myLine != '':
        res = myLine.split('      ')
        data = {'twitterId': res[0],
                'id': res[1],
                'date': res[2],
                'message': res[3]
                }
        f.write(json.dumps(data) + '\n')
    f.close()
print('failed: {} out of {}').format(failed, total)

