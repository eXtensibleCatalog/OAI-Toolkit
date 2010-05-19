#!/usr/bin/perl
###################################################################################################
# III Millennium Data Offload Script
# Written by Stephen Westman, adapted from code by Adam Brin
#
# To use this script, you will at least need to have Perl installed with the perl-Expect (yum) or
#  Expect.pm (CPAN).  Note that you MAY also need to have Expect installed (we already did, so I don't
#  know if this library will work without it).  It also needs to be loaded on your XC server so that
#  the MARC records can be transferred FROM III TO the XC server.  You also need to have an FTP server
#  on the XC box that will accept connections from you Millennium box (with the username and password
#  that you include below to log in)
#
# One other thing: this script has been developed on a Fedora Linux server so it utilizes Linux-based
#  conventions (such as \n instead of \r\n\ line breaks).  There may also be some system functions that
#  may not be available on Windows or Mac machines.  There is no desire to put these systems down.
#  It's just that Fedora Linux what we use here at UNC Charlotte 
#
# Note that this has been written for use at UNC Charlotte with our own set of installed features,
#  which may mean that in some ways things may need to be tweaked.  Also, we don't use login and
#  password to get into the system, intead relying on IP address checking.  If you use logins, you
#  will need to add code to handle that.
#
# While there are a number of features that are still under development, we didn't want to wait
#  until they were done before releasing what we have (as they say, "the perfect is the enemy of
#  of the good").  These features include:
#     1) handling the situation where no records are retrieved.  This can be a problem in that:
#        - you lose your workspace in Millennium (and somebody else may take it over)
#        - it will create problems in the sending of records in that the program will look to
#           send a file that doesn't exist, causing the program to hang
#        the code will check at the end of each run of the file creation problem and, if no
#        records were returned, it will create a file with a single record.  The program
#        will also return to the next file type so that no files will be sent to the XC server.
#        Our hope is to have it finished within a few weeks  
#     2) emailing the XC administrator concerning the status of each data load once it has 
#        finished
#     3) Writing the last day that the file has been downloaded.  That way, the next time that
#        the script is run, that date can be the $earliest_date value.  At this point, we just
#        make allowances for the script not running after Saturday or Sunday. 
#
# Also, this script has been tested and seems to work with our setup.  That does not mean that 
#  it will work properly in your environment for reasons already stated.  My suggestion is to
#  do two things:
#     - On a Linux or Macintosh (or perhaps Windows using Cygwin), use `tail -f` from the command
#        line of your XC server.  It will show you what the screens that you would see if STDOUT
#        was being sent to the screen
#     - Obtain a copy of the Expect.pm documentation from 
#           http://search.cpan.org/~rgiersig/Expect-1.15/Expect.pod
#       or search for "Expect.pm" in Google.  This documentation is invaluable in helping you to
#       find other ways of doing things (which is often necessary). 
#  
# One other thing: the day that the script is run will get the work done from the PREVIOUS 24 hour
#  period.  Thus it does not run on Monday (since there probably was little to no work done on
#  Sunday).  Also, the download for Friday is run on Saturday morning.  Note that this may cause
#  a bit of confusion in reading the script.
#
# Also note that I have added debugging statements that will print where you are in a given
#  process to STDOUT.  This helps in isolating where problems may be hiding.
###################################################################################################
use strict;
use Expect;

my $server = "iii-server.institution.edu";       # Name of Millennium server's telnet interface
my $filenum = 69;                      # Work area for bib output. Note that once you are sure 
                                       #  everything is working correctly, you can have all three
                                       #  of these work areas use the same work area number
my $sc_filenum = 29;                   # Work area for suppressed/created records
my $sm_filenum = 63;                   # Work area for suppressed/modified records
my $debug = shift || 0;
my $testing = 0;                       # Set testing (which allows you to search only a subset of the
                                       #  of the entire database)
my @my_test_range = ("\n");            # First bib number (without .b) record of test range
my @my_test_range2 = ("\n");           # Last record of test range (use "\n" alone to have it be the 
                                       #  last record)

# Email parameters for sending email that load was successful (not yet implemented)
my $to = 'admin@xc.institution.edu';         # Email address to whom emails should be sent 
my $from = 'xcuzr@xcbox.institution.edu';    # User from whom the records are sent
my $cc = '';                                 # Any cc's that you want to include

# Values to used in the script
my $tmpdir = '/tmp';                # Location for temporary files
my $logfile = $tmpdir . "/newbooks.txt";   # Location of log file.  Note that you can do a tail -f
                                             # on this file while the script is being run to see what
                                             # what is happening "behind the scenes"
my $xc_server = "xcserver.institution.edu";   # IP addresss/name of XC box
my @exp_user = ( "username\n");          # username on XC box (for transferring files to the XC box)
my @exp_pass = "password\n";             # password on XC box
my $filename = "bibfile.txt";            # Filename for main bib file offload
my $sc_filename = "sup_cre.txt";         # Filename for suppressed created records
my $sm_filename = "sup_mod.txt";         # Filename for suppressed modified records

my $init = 'init_user';                  # Initials for getting into lists and MARC
my $ipass = 'init_password';             # output systems

# When searching for suppressed date, to avoid having 0 records found (which is 
#  not yet handled by the code), you need to set this to a day at least 3-4 months
#  ago where there will be new records that will have been suppressed and updated
#  records modified in the system since that date 
my $suppresseddate = "010110";


#This section no longer needs to be modified
#------------------------------DO NOT MODIFY------------------------------------------------------#
my $number = 0;                          # Initialize $number 

my $start = localtime time;

# Get today's date to make calculations for date ranges for download parameters
my $time = time;
my $time1 = $time;
my $time2 = $time;
my $time3 = $time;

# Get the day of the week on which this is being run (to see if it is a weekend date
#  or after the weekend, thereby allowing for running three dates)
my @datetime = localtime($time3);
my $day = $datetime[6];

# Set $time to the previous day

# If today ($day set above) is Tuesday (==2), then set the following values:
#  - $time for 24 hours (in seconds) ago (i.e. Monday)
#  - $time1 for 72 hours ago (i.e. Sunday)
#  - $time2 for 48 hours ago (i.e. Saturday)
# Otherwise set all values to 24 hours ago
if ( $day == 2 ) {
   $time = time - 86400;
   $time1 = time - 172800;
   $time2 = time - 259200;
} else {
   $time = time - 86400;
   $time1 = time - 86400;
   $time2 = time - 86400;
}

# Create array @tim for $time using Perl's localtime() function
my @tim = localtime($time);
$tim[5] +=1900;
$tim[4] +=1;
if ( $day == 0 || $day == 1 ) {  # If today is Sunday or Monday (i.e. if
                                   the updates are from Saturday or Sunday)
   exit;
}
# Use $tim to populate elements of $latest date
my $latestdate = sprintf ("%02d%02d%02d", $tim[4],$tim[3],substr( $tim[5],2,2));


#########################################################################
# The following two values are set only for use in covering 2 day weekends.
#  Code to cover other eventualities will be forthcoming.
#########################################################################

# Create array @tim for $time1 using Perl's localtime() function
@tim = localtime($time1);
$tim[5] +=1900;
$tim[4] +=1;
# Use $tim to populate elements of $middledate date (for use for first backup
#  after the weekend)
my $middledate = sprintf ("%02d%02d%02d", $tim[4],$tim[3],substr( $tim[5],2,2) );

# Create array @tim for $time1 using Perl's localtime() function
@tim = localtime($time2);
$tim[5] +=1900;
$tim[4] +=1;
# Use $tim to populate elements of $earliestdate date - the first of the three
#  days of the weekend
my $earliestdate = sprintf ("%02d%02d%02d", $tim[4],$tim[3],substr( $tim[5],2,2) );

# Uncomment these to make sure that the appropriate values are being set
#print $earliestdate, "\n\n";
#print $middledate, "\n\n";
#print $latestdate, "\n\n";

# Initialize $delfile value - used to handle the possibility of a file already 
#  being present and needing to be deleted
my $delfile = 0;



&createItemLists($init,$ipass,'new','bibs');           # Create list of new/modified bibs
&createItemLists($init,$ipass,'new','suppressed_c'); # Create list of suppressed/created records
&createItemLists($init,$ipass,'new','suppressed_m'); # Create list of suppressed/modified records

sub createItemLists( ) {

   my $initials = shift;      # iii initials
   my $initpassword = shift;  # iii password
   my $mode = shift;    # append|new  -- erase the list?
   my $type = shift;    # for different types of lists
#   my $output = "yes";     # whether to output the file?
#   my $line = "";

   #Execute an instance of telnet and set the logfile.  Set "w" for the
   #  first time through and then leave it blank so that it will append
   #  in subsequent passes through the function
   my $exp = Expect->spawn('telnet',$server);
   if ( $type eq "bibs" ) {
      $exp->log_file($logfile,"w");
   } else {
      $exp->log_file("$logfile");
   }
print "Logged in\n";   
   $exp->log_stdout(0) if ($debug < 1);
   if ($debug > 2) { $Expect::Debug=1; $Expect::Exp_Internal=0;}
   my $t=10;
   
   # Begin navigation through the telnet-based interface to create a download
   #  set of records
   $exp->expect($t, "Choose one");  # Content of initial menu
   print $exp "M";                  # Select "M" for MANAGEMENT Information
   $exp->expect($t,"S,M,Q");
print "running List function\n";
   print $exp "L";                  # Create LISTS of records menu option
   $exp->expect($t,"Please key your initials");    # Enter username
   print $exp "$initials\r";
   $exp->expect($t,"Please key your password");    # Enter password
   print $exp "$initpassword\r";
   $exp->expect($t,"Choose one");

   # Specify the number of the work area defined to which records should be 
   #  saved (set in the variables above) and set timeout to 10 seconds
   if ( $type eq "bibs" ) {
      $filenum = $filenum;
   } elsif ( $type eq "suppressed_c" ) {
      $filenum = $sc_filenum;
   } elsif ( $type eq "suppressed_m" ) {
      $filenum = $sm_filenum;
   }
   print $exp "$filenum";
   $t = 10;

   $delfile = -1;

   # Handle two possible scenarios - one where xc.out is already in the list
   #  of files that have been created and one where it isn't.  We do this by
   #  checking the options presented by the $exp-expect() command.  In the first
   #  case, if the prompt comes with `E,R,X,U,Q` (or whatever your system gives
   #  as prompts that request what to do with a pre-existing file.  NOTE: if
   #  your options are different, you will need to change the values below), 
   #  the variable $delfile is set to 1.  If, on the other hand, the prompt 
   #  is for how to create the file (if prompt is 1,2,Q), that means that the 
   #  file is not already there (and $delfile is set to 0).
   $exp->expect($t,
        ["E,R,X,U,Q"=> sub {$delfile = 1;}],
        ["1,2,Q"=> sub {$delfile = 0;}]
        );

   #This section handles whether there is a pre-existing file (or where $delfile
   #  is equal to 1.  If so, it takes steps to eliminate that record.
   if ($delfile > 0) {
      print $exp "E";            # Select "EMPTY your review file"
      $exp->expect($t,"y/n");    # Are you SURE?
      print $exp "y";
      # Changed
      $exp->expect($t,"Choose one (N,C,Q)"); # Select "NEW BOOLEAN search"
      print $exp "N";
   } else {
      $exp->expect($t,"Choose one");         # Select "Create a new file"
      print $exp "2";               
   } # if delfile !> 0

print "Selecting type of records to download\n";     # STDOUT debugging to tell where you are

   # Here we select what type of record we are going to be selecting from: item, bib,
   #  circulation, etc.  When prompted, we answer B for Bibliographic
   $exp->expect($t,"Choose one"); # Select type of output you want
   print $exp "B";     # From "BOOLEAN SEARCH of FILE to create a LIST of records" menu

   # We now set the parameters for the search, depending on the $type defined in the
   #  call to the CreateItemLists() function
   $exp->expect($t,"Enter code in front of desired field");
print "Inputting parameters\n";     # STDOUT debugging to tell where you are
   
   if ( $type eq "bibs" ) {
      # Here we begin building the list of parameters for the daily search.  We look
      #  for those which were either created or modified on $second date (yesterday)
      if ( $testing == 1 ) {
         $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
         my @range = "\\";
         $exp->send( @range );
         $exp->expect($t,"Search records contained in another review file?");
         print $exp "n";
         $exp->expect($t,"Enter starting");
         $exp->send_slow( 2, @my_test_range );
         $exp->expect($t,"Enter ending");
         $exp->send_slow( 2, @my_test_range2 );
         $exp->expect($t,"Is the range");
         print $exp "y";
      }
      
      $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
      print $exp "10";
      $exp->expect($t,"CREATED");
      print $exp "=";
      $exp->expect($t,"mo-dy-20yr");
      print $exp "$latestdate\r\n";
      $exp->expect($t,"range for searching");
      print $exp "O";
      $exp->expect($t,"Enter code in front of desired field");
      print $exp "11";
      $exp->expect($t,"UPDATED");
      print $exp "=";
      $exp->expect($t,"mo-dy-20yr");
      print $exp "$latestdate\r\n";
      $exp->expect($t,"range for searching");
      
      # As noted, since no records are done on Saturday or Sunday, we don't run this
      #  script for those days.  Since this is run after midnight to include anything
      #  done on that day, we add the middle date (day before yesterday) and two days
      #  ago to include anything that might have been done on those two days.  
print "Entering Tuesday multidate subroutine\n";    # STDOUT debugging to tell where you are
      if ( $day == 2 ) {
         print $exp "O";
         $exp->expect($t,"Enter code in front of desired field");
         print $exp "10";
         $exp->expect($t,"CREATED");
         print $exp "=";
         $exp->expect($t,"mo-dy-20yr");
         print $exp "$middledate\r\n";
         $exp->expect($t,"range for searching");
         print $exp "O";
         $exp->expect($t,"Enter code in front of desired field");
         print $exp "11";
         $exp->expect($t,"UPDATED");
         print $exp "=";
         $exp->expect($t,"mo-dy-20yr");
         print $exp "$middledate\r\n";
         $exp->expect($t,"range for searching");
         print $exp "O";
         $exp->expect($t,"Enter code in front of desired field");
         print $exp "10";
         $exp->expect($t,"CREATED");
         print $exp "=";
         $exp->expect($t,"mo-dy-20yr");
         print $exp "$earliestdate\r\n";
         $exp->expect($t,"range for searching");
         print $exp "O";
         $exp->expect($t,"Enter code in front of desired field");
         print $exp "11";
         $exp->expect($t,"UPDATED");
         print $exp "=";
         $exp->expect($t,"mo-dy-20yr");
         print $exp "$earliestdate\r\n";
         $exp->expect($t,"range for searching");
      }
   
   # If type is suppressed_c (suppressed and created), enter this block
   } elsif ( $type eq "suppressed_c" ) {
      if ( $testing == 1 ) {
         $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
         my @range = "\\";
         $exp->send( @range );
         $exp->expect($t,"Search records contained in another review file?");
         print $exp "n";
         $exp->expect($t,"Enter starting");
         $exp->send_slow( 2, @my_test_range );
         $exp->expect($t,"Enter ending");
         $exp->send_slow( 2, @my_test_range2 );
         $exp->expect($t,"Is the range");
         print $exp "y";
      }
      
      $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
      print $exp "10";
      $exp->expect($t,"CREATED");
      print $exp "G";
      $exp->expect($t,"mo-dy-20yr");
      print $exp ("$suppresseddate\r\n");
      $exp->expect($t,"range for searching");
      print $exp "A";
      $exp->expect($t,"Enter code in front of desired field");
      print $exp "7";
      $exp->expect($t,"SUPPRESS");
      print $exp "~";
      $exp->expect($t,"SUPPRESS <>");
      print $exp "-";
      $exp->expect($t,"range for searching");

   # If type is suppressed_m (suppressed and modified), enter this block
   } elsif ( $type eq "suppressed_m" ) {
      if ( $testing == 1 ) {
         $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
         my @range = "\\";
         $exp->send( @range );
         $exp->expect($t,"Search records contained in another review file?");
         print $exp "n";
         $exp->expect($t,"Enter starting");
         $exp->send_slow( 2, @my_test_range );
         $exp->expect($t,"Enter ending");
         $exp->send_slow( 2, @my_test_range2 );
         $exp->expect($t,"Is the range");
         print $exp "y";
      }      
      $exp->expect($t,"Find BIBLIOGRAPHIC records that satisfy the following conditions");
      print $exp "11";
      $exp->expect($t,"UPDATED");
      print $exp "G";
      $exp->expect($t,"mo-dy-20yr");
      print $exp ("$suppresseddate\r\n");
      $exp->expect($t,"range for searching");
      print $exp "A";
      $exp->expect($t,"Enter code in front of desired field");
      print $exp "7";
      $exp->expect($t,"SUPPRESS");
      print $exp "~";
      $exp->expect($t,"SUPPRESS <>");
      print $exp "-";
      $exp->expect($t,"range for searching");
   }

   # Now that we have selected the parameters for searching the bib file,
   #  we press/send "S" to tell the system to start the search.  
   print $exp "S";
print "Getting filename and starting search\n";    # STDOUT debugging to tell where you are

   # We then provide it with the name of the file to which we want the
   # system to which we want it to save the results (using the $filename
   #  variable defined above)         
   $exp->expect($t,"What name would you like to give this file of records?");
   if ( $type eq "bibs" || $type eq "test") {
      print $exp "$filename\r\n";
   } elsif ( $type eq "suppressed_c" ) {
      print $exp "$sc_filename\r\n";
   } elsif ( $type eq "suppressed_m" ) {
      print $exp "$sm_filename\r\n";
   }  
   # Next, since it can take a while to go through the entire database, we
   #  up the timeout alloted for this step to an hour (in number of seconds).
   #  While set time out to one hour, those with larger databases may want
   #  to give this a larger amount of time
   $t=7200;
   $exp->expect($t,"Press <SPACE>");
   print $exp " " ;
print "Search completed and exiting\n";  # STDOUT debugging to tell where you are        
   $t = 3;

   # Once the search has been created, we return to the initial menu
   $exp->expect($t,"Choose one");
   print $exp "Q";
   $exp->expect($t,"Choose one");
   print $exp "Q";
   print $exp "Q";



   ###########################################################################
   # We have now completed the file creation process.  We now proceed to sending
   #  the records to the xc server.  We do so by backing out back to the initial
   #  menu.
   ###########################################################################
   # From the initial screen, we now choose "A" for ""Additional system
   #  functions".  We then chose the "M" option to "Read/write MARC records"
   #      $exp->expect($t,"K,A,Q");
   print $exp "";
   $exp->expect($t,"Choose one");
   print $exp "A";               # Choose ADDITIONAL system functions
      
   # Once we have logged in, press/send "M" to "Output MARC records to 
   #  another system using FTS".
   $exp->expect($t,"K,A,Q");
   print $exp "M";               # Select Read/write MARC records
   
   # Next, we send our credentials to access the output system
   $exp->expect($t,"Please key your initials");
   print $exp "$initials\r";
   $exp->expect($t,"Please key your password");
   print $exp "$initpassword\r";
   
   # Once we are in, we define the name of the output file we want
   #  to create

   my $fname = "";
   $exp->expect($t,"Choose one");
   print $exp "M";            # Output MARC records to another system using FTS
   $exp->expect($t,"Choose one");
   print $exp "C";            # CREATE disk file of unblocked MARC records
   
   # Next, you use the name defined at the top of the program for this $type of
   #  record and send it to Millennium
   $exp->expect($t,"Enter name of file");
   if ( $type eq "bibs") {
      my @fname = ("$filename" . "\r\n");
      $exp->send( @fname );
   } elsif ( $type eq "suppressed_c" ) {
      my @fname = ( "$sc_filename\r\n" );
      $exp->send( @fname ); 
   } elsif ( $type eq "suppressed_m" ) {
      my @fname = ( "$sm_filename\r\n" );
      $exp->send( @fname ); 
   }
   
   # At this point, that file either already exists or it doesn't.  Here we
   #  check the response and, if we get a `y/n`, it means that it was there
   #  (and )is now deleted) and that we need to make sure that it is 
   #  eliminated.
   $exp->expect($t,
        ["y/n"=> sub {$delfile = 1;}],
        ["R,B,Q"=> sub {$delfile = 0;}]
        );
print "Get file number";      # STDOUT debugging to tell where you are
   if ($delfile > 0) {        # In other words, the file existed
      print $exp "E";         # Selects "EMPTY your print file"
      $exp->expect($t,"y/n"); # Are you sure?
      print $exp "Y";
      $exp->expect($t,"y/n"); # Are you REALLY sure?
      print $exp "y";
      print $exp ($t, "B");   # Select "from a BOOLEAN review file"
      # Changed
   } else {                   # If it did not exist, continue as normal
      print $exp ($t, "B");   # Select "from a BOOLEAN review file"
   } # if delfile !> 0

   # Since the list of available print files is usually less than the number
   #  of work areas, we can't rely on the work area defined above to contain
   #  the file that we need.  We therefore create a variable - $workarea -
   #  to allow us to go through all of the print areas and write their contents
   #  to a file (from which we can obtain the print file number).

   # In either case - whether we have deleted a prexisting file or there is no
   #  file there to begin with, the next screen asks what source of records do we
   #  want to use.  We select B for "from a BOOLEAN review file" 
   my @next = ("F");
   $exp->expect($t, "Choose one");
   my $workarea = $exp->before();
   $exp->send( @next );
   $exp->expect($t, "F,Q");
   $workarea .= $exp->before();
   
   # Note that, for some reason, using the normal `print $exp->expect( $t, "F")`
   #  method does not work in this part of Millenium (it uses number in $t, the 
   #  timeout variable, as the indicating file area of what it is that we want.  
   #  We therefore have to utilize the $exp->send( @array ) method to send the 
   #  value so that it is received properly by Millenium.  We begin by defining 
   #  the single element array, @next.
   $exp->send( @next );
   $exp->expect($t, "Choose one");
   $workarea .= $exp->before();

   $exp->send( @next );
   $exp->expect($t, "Choose one");
   $workarea .= $exp->before();

   $exp->send( @next );
   $exp->expect($t, "Choose one");
   $workarea .= $exp->before();

   $exp->send( @next );
   $exp->expect($t, "Choose one");
   $workarea .= $exp->before();
   $exp->send( @next );

print "Made it this far!";
   
   # Next we go through $workarea to which we saved the screens to find the file area
   #  where $filename has been saved.  We begin by initializing it.  We open up the
   #  $num_fname file for reading.
   my $fname_pos = index( $workarea, " > $filename" );
   my $pos = -1;
   if ( $type eq "bibs") {
      $fname_pos = index( $workarea, " > $filename" );
   } elsif ( $type eq "suppressed_c" ) {
      $fname_pos = index( $workarea, " > $sc_filename" );
   } elsif ( $type eq "suppressed_m" ) {
      $fname_pos = index( $workarea, " > $sm_filename" );
   }
   my $num_pos = $fname_pos - 2;
   $number = substr( $workarea, $num_pos, 2 );
   
   # Since print area numbers may be only one digit, and the character before them
   #  is (at least in our system) and H, we look for the position of H in the two
   #  character $number we have obtained.  If it is a two digit number, $h_pos will
   #  be -1.  However, if it is a single digit number, $h_pos will be 0.  Thus, if
   #  it is 0, we ascribe only the first of the characters to $number
   my $h_pos = index( $number, "H" );
   if ( $h_pos == 0 ) {
      $number = substr( $number, 1, 1 );
   }
   my @fnumero = ($number); 
   $exp->clear_accum();
   $exp->send( @fnumero);
   print "\n\n" . $number . "\n";

print "Start sending records\n";    # STDOUT debugging to tell where you are
   $t=10;
   $exp->expect( $t, "I,S,N,Q");
   my @send = ("S");                # Start sending records
   $exp->send_slow( 2, @send );

   $exp->expect( $t, "Number of output records");  # Wait for sending of
                                                   # records to complete
   sleep 2;
   my @quit = ("Q");
   $exp->send_slow(2, @quit );
   $exp->expect($t,"Press <SPACE> to continue");
   my @space = (" ");                              # Send space character
   $exp->send_slow( 2, @space );
   $exp->expect( $t, "Q,R");
   $exp->send_slow(2, @quit );
   $exp->expect($t,"Press <SPACE> to continue");
   $exp->send_slow( 2, @space );
print "Back to print file menu\n";  # STDOUT debugging to tell where you are

   # We now send the file that we just created to the XC box for
   #  processing by the OAI Toolkit.  We first of all have to find
   #  out what the file number is of the print file that we want to 
   #  send to XC box.  We do that by using using the $exp->before()
   #  Perl::Expect function to parse the file list to see what the
   #  file number matching the file name we want to send is.
   $exp->expect( $t, "Choose one");
   my $v = $exp->before();    # read the screen contents into $v
                              # Then, depending on the $type, we
                              # find out where in $v the filename
                              # begins.  Note that we include the
                              # " > " before the filename so that
                              # the character before it will be the
                              # file number.  Note that this assumes
                              # that you will only have single digit
                              # file numbers.  If you could have
                              # double digits (i.e. >= 10 ) you will
                              # need to modify the code below
   $fname_pos = 0;
   $pos = -1;
   if ( $type eq "bibs") {
      $fname_pos = index( $v, " > $filename" );
   } elsif ( $type eq "suppressed_c" ) {
      $fname_pos = index( $v, " > $sc_filename" );
   } elsif ( $type eq "suppressed_m" ) {
      $fname_pos = index( $v, " > $sm_filename" );
   }

   # We first of all, find out what the position of " > (name of file)" is 
   my $fnum = substr( $v, $fname_pos-1, 1 ); # Then take the substring of $v
                                             # and get the value at $fname_pos
                                             # minus one.  NOTE, as indicated
                                             # above, if you might have a double
                                             # digit file number,  you will need
                                             # to modify this code slightly

   print "\n$fnum\n";
   print $exp "S";                  # SEND a MARC file to another system using FTS
   $exp->expect( $t, "Enter file number ");
   my @fname_ary = ("$fnum");       # Send the filenumber to send to Millennium
   $exp->send ( @fname_ary );

   $exp->expect($t, "Q,E,D");       # FILE TRANSFER SOFTWARE menu with hosts
print "$xc_server\n";               # Print out to $xc_server to STDOUT
   my $y = $exp->before();          # use $exp_before to populate $y with the contents
                                    # of the screen before the "expected" string was
                                    # found and save to $y
   my $ip_pos = index( $y, $xc_server);   # Locate where in $y the $xc_server IP address
                                          # is located and save the position to $ip_pos
   
   my $server_pos = $ip_pos - 5;          # The position of the server number is 5 
                                          # characters before the $ip_pos  
   print $server_pos;                     # Output the $server_pos to STDOUT so that we
                                          # can verify that it was found

   my $server_str = substr( $y, $server_pos, 2 );  # Get the first to characters at
                                                   # $server_pos
   my $zero_pos = index( $server_str, "0" );       # See if first character is a 0 (meaning
                                                   # that it is less than 10).
   my $servernum = "";
   if ( $zero_pos == 0 ) {                         # If first character is a 0, then strip it
                                                   # out.  Otherwise (i.e. if it is >= 10),
                                                   # use both characters
      $servernum = substr( $server_str, 1,1);
   } else {
      $servernum = substr( $server_str, 0, 2);
   }
   print $servernum;                      # Print $servernum to STDOUT so we can verify that we
                                          # have found it

   my @serverno = ( $servernum );         # Send servernum to Millenium
   $exp->send( @serverno );
   print "$servernum\n";

   $t=60; 
   $exp->expect( $t, "Username:");
   $exp->send_slow( 2,  @exp_user );
   $exp->expect( $t, "Password");
   $exp->send_slow( 2, @exp_pass );
   $exp->expect( $t, "T,M,P");
   my @transfer = ("T");
   $exp->send_slow( 2, @transfer );
print "Transfer\n";                        # STDOUT debugging to tell where you are
   $exp->expect( $t, "Enter name of remote file");
   print $exp "\n";
   my @continue = ("C");
   $exp->expect( $t, "C,S");
   $exp->send_slow( 2, @continue );
print "Continue\n";                        # STDOUT debugging to tell where you are
   $exp->expect( 2, "T,M,P)");
   @quit = "Q";
   $exp->send_slow(2, @quit);
   $exp->expect($t,"Press <SPACE>");
   print $exp " ";
   $t = 30;
   $exp->expect($t,"Choose one");
   print $exp "Q";
   $exp->expect($t,"Choose one");
   print $exp "Q";
   $exp->expect($t,"Choose one");
   print $exp "Q";
   $exp->expect($t,"Choose one");
   print $exp "Q";
   $exp->soft_close();
}
print "Ended\n";
exit;
