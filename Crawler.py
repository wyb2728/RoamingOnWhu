import pandas as pd
import requests
import json

key='7f5443757c6cced017b0dcd666711d9c'

inpath=r"C:Desktop\1.xlsx"
outpath=r"C:Desktop\2.xlsx"

df = pd.read_excel(inpath, header=None)
df['longitude'] = ''
df['latitude'] = ''
df['district'] = ''

def get_location(address):
    url = 'https://restapi.amap.com/v3/geocode/geo'
    params = {'key':key, 'address':address}
    res = requests.get(url, params)
    data = json.loads(res.text)
    if data['status'] == '1' and data['geocodes']:
        location = data['geocodes'][0]['location']
        district = data['geocodes'][0]['district']
        return location.split(','), district
    else:
        return None, None

for i, row in df.iterrows():
    address = "武汉市" + row[0]
    location, district = get_location(address)
    if location and district :
        df.loc[i,'longitude'] = location[0]
        df.loc[i,'latitude'] = location[1]
        df.loc[i,'district'] = district

df.to_excel(outpath, index=None, header=None)
