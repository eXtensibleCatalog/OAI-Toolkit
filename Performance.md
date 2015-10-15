**current performance of the oai-toolkit version 1.0.7 (MST as harvester)**

| **server** | **repo size (records)** | **repo size** | **records per hour** <sup>2</sup> | **OS** |  **64/32 bit** | **memory allocated to jvm** | **memory on server** | **cpu** | **disk** | **virtual/physical** |
|:-----------|:------------------------|:--------------|:----------------------------------|:-------|:---------------|:----------------------------|:---------------------|:--------|:---------|:---------------------|
| CARLI      | 21,312,626              | 43G           | 3.6M                              | Ubuntu 8.04.4 LTS | 64             | 12G                         | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |
| CARLI      | 12,943,660              | 29G           | 4.2M                              | Ubuntu 8.04.4 LTS | 64             | 9G                          | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |

**performance of previous versions of the oai-toolkit (perl script <sup>3</sup> as harvester)**

| **server** | **repo size (records)** | **repo size** | **records per hour** <sup>2</sup> | **OS** |  **64/32 bit** | **memory allocated to jvm** | **memory on server** | **cpu** | **disk** | **virtual/physical** |
|:-----------|:------------------------|:--------------|:----------------------------------|:-------|:---------------|:----------------------------|:---------------------|:--------|:---------|:---------------------|
| UR-137     | 6,684,594               | 15G           | 1M                                | Solaris | 32             | 2.2G                        | 3G                   | 3 GHz dual-core processor | 10k RPM  | virtual              |
| UR-137     | 6,684,594               | 15G           | --                                | Solaris | 32             | 2.2G                        | 3G                   | (2) 2400MHz Intel(r) Xeon(r) <sup>1</sup> | 10k RPM  | virtual              |
| CARLI      | 6,684,594               | 15G           | 2.1M                              | Ubuntu 8.04.4 LTS | 64             | 3G                          | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |
| CARLI      | 21,312,626              | 43G           | 900k                              | Ubuntu 8.04.4 LTS | 64             | 9G                          | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |
| CARLI      | 21,312,626              | 43G           | 5.2M <sup>4</sup>                 | Ubuntu 8.04.4 LTS | 64             | 9G                          | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |
| CARLI      | 21,312,626              | 43G           | 1.2M                              | Ubuntu 8.04.4 LTS | 64             | 12G                         | 32G                  | 2.7 GHz Quad-Core AMD |          | physical             |


**notes**
  1. UR-137 used to have a 3.0GHz Dual-core, now it has changed to (2) 2400MHz (single core) processors, the data shown is for the 3.0GHz proc.  Initially testing shows the 2 2400MHz processors will produce even slower results.
```
voyager@xcvoyoai2 > psrinfo -pv
The physical processor has 1 virtual processor (0)
  x86 (GenuineIntel family 6 model 15 step 8 clock 2400 MHz)
        Intel(r) Xeon(r) CPU           E7340  @ 2.40GHz
The physical processor has 1 virtual processor (1)
  x86 (GenuineIntel family 6 model 15 step 8 clock 2400 MHz)
        Intel(r) Xeon(r) CPU           E7340  @ 2.40GHz
```
    * http://www.intel.com/cd/channel/reseller/asmo-na/eng/products/server/processors/5100/feature/index.htm
  1. maximum number of records in a `ListRecords` response chunk value set to 5000
  1. Harvest script used to generate timings:
```
#!/usr/bin/perl
use LWP::UserAgent;
use HTML::Parse;

$URL = 'http://xc.ilcso.uiuc.edu:8080/OAIToolkit/oai-request.do?verb=ListRecords&metadataPrefix=marc21';
$RES_URL = 'http://xc.ilcso.uiuc.edu:8080/OAIToolkit/oai-request.do?verb=ListRecords&resumptionToken=';

my $old_fh = select(STDOUT); 
$| = 1;
select($old_fh); 

my $ua = new LWP::UserAgent;
$ua->timeout(300);

my $request = HTTP::Request->new('GET');
print "requesting ${URL} ...\n";
$request->url($URL);

my $now;
my $before = time;

print `date`;
my $response = $ua->request($request);

while ($response->content =~ /<resumptionToken cursor=".+" completeListSize=".+">([^<]+)<\/resumptionToken>/) {
   my $token = $1;

   $now = time;
   print ($now - $before);
   print " seconds\n";

   print "requesting ${RES_URL}${token} ...\n";
   $request->url($RES_URL . $token);
   $before = time;
   print `date`;
   $response = $ua->request($request);
}
```
  1. Using version 1.0.3 of the OAI Toolkit which now offers a ["cached full initial harvest"](OptimizedInitialHarvest.md) optimization