#!/bin/sh

# upload apk to hockey app
curl \
  -F "status=2" \
  -F "notify=1" \
  -F "ipa=@app/build/outputs/apk/release/app-release.apk" \
  -H "X-HockeyAppToken: e3efccfc7a0942339d1fc2d662aae102" \
  https://rink.hockeyapp.net/api/2/apps/upload