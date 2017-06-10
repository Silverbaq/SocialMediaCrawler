import json

input = 'profiles.txt'
output = 'profiles-re.txt'


with open(input, "r") as ins:
    f = open(output, 'a')
    for line in ins:
        ls = line.split('     ')
        l = [k.strip() for k in ls]
        data = {
                'id': l[0],
                'name': l[1],
                'nickname': l[2],
                'image': l[3]
               }

        f.write(json.dumps(data)+'\n')
    f.close()

