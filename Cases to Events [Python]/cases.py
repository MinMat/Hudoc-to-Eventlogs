import os

cases = ['CASE OF CARVALHO PINTO DE SOUSA MORAIS v. PORTUGAL',
        'CASE OF GARIB v. THE NETHERLANDS',
        'CASE OF K_ROLY NAGY v. HUNGARY',
        'CASE OF M v. THE NETHERLANDS',
        'CASE OF REGNER v. THE CZECH REPUBLIC',
        'CASE OF WENNER v. GERMANY',
        'CASE OF D.L. v. BULGARIA',
        'CASE OF G_ZELYURTLU AND OTHERS v. CYPRUS AND TURKEY',
        'CASE OF LOPES DE SOUSA FERNANDES v. PORTUGAL',
        'CASE OF NAGMETOV v. RUSSIA',
        'CASE OF KORNEYKOVA AND KORNEYKOV v. UKRAINE',
        'CASE OF CANG_Z AND OTHERS v. TURKEY',
        'CASE OF A.N. v. LITHUANIA',
        'CASE OF R.B. v. HUNGARY',
        'CASE OF TADDEUCCI AND McCALL v. ITALY',
        'CASE OF INCIN v. TURKEY',
        'CASE OF V.M. AND OTHERS v. BELGIUM',
        'CASE OF CHIRAGOV AND OTHERS v. ARMENIA',
        'CASE OF SARGSYAN v. AZERBAIJAN',
        'CASE OF KHAN v. GERMANY']

for case in cases:
    call = 'python comprehend.py "' + case + '"'
    print(call)
    os.system(call)
    call = 'python extractDateEvents.py "./Comprehend Json/comprehend-' + case + '.json"'
    print(call)
    os.system(call)
    call = 'python eventJsonToHTML.py "./Events Json/events-' + case + '.json"'
    print(call)
    os.system(call)
    
