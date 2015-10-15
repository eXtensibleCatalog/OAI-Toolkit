# Performing an Optimized Initial Harvest #

Once you have the OAI server up and running, inevitably you will come to the point at which you need to perform a full harvest.  Be sure to take advantage of the toolkit's initial harvest optimizations and save a lot of processing time.

The toolkit will create a full harvest cache upon receiving the first request for a full harvest.  Here's a way to determine if your harvest is taking advantage of this cache:

```
cd [tomcat]/logs
tail -f catalina.out | grep harvest
```

If you see the following log entry during your harvest requests, this means you are indeed taking advantage of the optimization:

```
[PRG] We are using the cached full harvest for extra speed! (That's good!)
```

If, however, you see the following instead, it means you are not:

```
[PRG] We are not using the cached full harvest. (Standard query.)
```

You can solve this by simply restarting the tomcat server and restarting your harvest.

## Technical Details ##

Here’s how the full harvest cache works:

First of all, the full repository cache is created as soon as the first harvester requests records (issues a ListRecords verb).

Here are the scenarios in which a harvester WILL be able to take advantage of this cache:

- If the request contains neither a “from” nor an “until” parameter and is issued before any new records are imported/edited/deleted into the repository (since the cache was created).

- If the request contains a “from” parameter that is older than the oldest (first) record imported into the repository.

- If the request contains an “until” parameter that is more recent than the most recently imported/edited/deleted record in the repository.

- If the request contains both “from” and “until” parameters and the above two conditions are true.

NOTES:

- Any request containing a “set” parameter WILL NOT be able to take advantage of the cache.

- It's OK if records get imported/edited/deleted after the harvest has begun.

- Initial harvests making use of this cache will remain valid (i.e., valid resumptionTokens) until apache tomcat is restarted.  A resumptionToken exception will be thrown if a harvest attempts to span tomcat restarts.