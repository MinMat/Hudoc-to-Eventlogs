import json
import sys
import os

# takes a case with aws comprehend entities in json
# produces a json containing a list of events
# an event is a sentence containing a date entity
class EventDetector():
    def __init__(self, caseJson):
        if 'CaseName' not in caseJson \
            or 'CaseText' not in caseJson \
            or 'Entities' not in caseJson:
            raise Exception('wrong json format!')

        self._caseJson = caseJson

    # produces a json containing all events of the case
    def detectEvents(self):
        # start the return json
        eventsJson = dict()
        eventsJson['CaseName'] = self._caseJson['CaseName']
        eventsJson['CaseText'] = self._caseJson['CaseText']
        # start a list of detected events
        events = []
        eventIdCounter = 0
        for entity in self._caseJson['Entities']:
            if entity['Type'] == "DATE":
                event = dict()
                # use a running counter for the eventId
                eventIdCounter += 1
                event['Id'] = eventIdCounter
                # the date of the new event is the text where 
                # aws comprehend detected the entity 
                event['Date'] = entity['Text']
                # save the sentence that contains the date
                event['Sentence'] = self._extractSentence(entity['BeginOffset'], entity['EndOffset'])
                events.append(event)

        eventsJson['Events'] = events
        return eventsJson

    # extracts the sentence the marked term is part of
    # out of the current case text
    def _extractSentence(self, beginOffset, endOffset):
        caseText = self._caseJson['CaseText']
        # expand the sentence into both directions until a period is found
        expandLeft = True
        expandRight = True
        while expandLeft or expandRight:
            if expandLeft:
                if beginOffset == 0:
                    expandLeft = False
                else:
                    beginOffset -= 1
                
                if self._isStopChar(caseText[beginOffset], beginOffset):
                    beginOffset += 1
                    expandLeft = False

            if expandRight:
                if self._isStopChar(caseText[endOffset], endOffset):
                    expandRight = False
                    continue
                endOffset += 1
        return caseText[beginOffset:endOffset]

    # check whether a character in the text is a character marking the start/end of a sentence
    def _isStopChar(self, char, position):
        if char == ':' or char == ';' or position < 3:
            return True

        if not char == '.':
            return False 

        caseText = self._caseJson['CaseText']

        # enumeration counts as period
        if caseText[position-1] in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            return True
        # check for abbreviation or triple dots
        if caseText[position-2:position] == 'no' or \
           caseText[position-3:position] == 'nos' or \
           caseText[position-2] == ' ' or \
           caseText[position-1] == '.' or \
           caseText[position-2] == '.' or \
           caseText[position+1] == '.' or \
           caseText[position+2] == '.':
           return False
            
        return True

fileName = sys.argv[1]
if not os.path.isfile(fileName):
    raise Exception(fileName + " does not exist")

if not os.path.isdir("Events Json"):
    os.makedirs("Events Json")

jsonData=open(fileName).read()
caseJson = json.loads(jsonData)

ed = EventDetector(caseJson)

with open("Events Json/events-" + caseJson['CaseName'] + ".json", 'w+') as f:
    f.seek(0)
    f.write(json.dumps(ed.detectEvents(), indent=4))

