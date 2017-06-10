
input = 'relations.txt'
output = 'relations-re.txt'

def saveline(line):
    f.write(line+'\n')

with open(input, "r") as ins:
    f = open(output, 'a')
    for line in ins:
        f.write(line.replace('      ',' '))
    f.close()

