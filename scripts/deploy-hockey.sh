#!/bin/sh

# upload apk to hockey app
curl \
  -F "status=2" \
  -F "notify=1" \
  -F "ipa=@app/build/outputs/apk/release/app-release.apk" \
  -H "X-HockeyAppToken: 5151aea345774ec4a891f1da9fed0891" \
  https://rink.hockeyapp.net/api/2/apps/upload