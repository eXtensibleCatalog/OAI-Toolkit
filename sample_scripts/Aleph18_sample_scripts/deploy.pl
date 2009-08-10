#!/usr/bin/perl
#
#  Author: Rick Johnson
#  Date:   05/11/2009
#
#  After untarring contents you run this script and it does the following: 
#
#   while iterating through sub-directories in current untarred directory
#       - create sub-directory in target directory if target subdirectory does not exist
#       - create archive directory in target sub-directory if archive directory does not exist
#		- iterate through files in root untarred directory and each sub-directory
#	while iterating through files in a directory
#		- archive existing copy of file if already exists in target directory with correct date and initials
#		- copy new file to target dir
#
#   This program intends to be passed the destination absolute path and initials for the person executing the script for
#   archive purposes.
#
# usage: deploy [destination absolute path] [initials]
#
#      example:  deploy /afs/nd.edu/user14/aleph rpj
#
use strict;
use File::Find;

if ($#ARGV < 1 ) {
	print "usage: Please specify [destination_absolute_path] [initials]\n";
	exit;
}

my $dest = $ARGV[0];
my $initials = $ARGV[1];

unless (-d $dest){
	die "Destination directory does not exist: ${dest}";
} else {
	print "Deploying files to ${dest} using initials ${initials}...$/";
}

#get timestamp for temp dir
(my $sec,my $min,my $hour,my $mday,my $mon,my $year,my $wday,my $yday,my $isdst) =
                                                localtime(time);

$year += 1900;
$mon++;
$mon = sprintf("%02d", $mon);
$mday = sprintf("%02d", $mday);
$hour = sprintf("%02d", $hour);
$min = sprintf("%02d", $min);
$sec = sprintf("%02d", $sec);

my $cur_dir = `pwd`; 
chomp($cur_dir);
find(\&deploy_files,$cur_dir);

#delete temporary files
#`cd ${temp_dir};rm -R *;cd ..; rmdir ${temp_dir}`;

exit;

#deploy all files in this untarred directory, make sure to skip installer
sub deploy_files(){
	#   while iterating through sub-directories in current untarred directory
#       - create sub-directory in target directory if target subdirectory does not exist
#       - create archive directory in target sub-directory if archive directory does not exist
#		- iterate through files in root untarred directory and each sub-directory
#	while iterating through files in a directory
#		- archive existing copy of file if already exists in target directory with correct date and initials
#		- copy new file to target dir
# check for error on any file operation
# if so, quit
# archive name = filename.date.index.initials
#
	my $filepath = $File::Find::name;
	my $path_suffix = $filepath;
	$path_suffix =~ s/$cur_dir//g;
	my $filedir = $File::Find::dir;
	my $filename = $_;
	#remove first character from path since we are translating the root to new target (first character will be a '.')
	my $target_path = $dest . $path_suffix;
	my $archive_path = $dest . $path_suffix . "/archive";
	my $archive_file = "${filename}.${year}${mon}${mday}${hour}${min}${sec}.${initials}"; 
	
	if (-d $filepath){
		#source is a directory...check if target directory exists if not root of source
		unless ($filename eq "."){
			unless (-d $target_path){
				#create directory
				my $result = system("mkdir ${target_path}");
				if ($result!=0){
					die "Failed to create target directory ${target_path}...quitting";
				} else {
					print "Created target directory ${target_path}$/";	
				}
			} else {
				print "Use existing target directory ${target_path}$/"
			}
		}
		unless (-d $archive_path){
			#create archive directory
			my $result = system("mkdir ${archive_path}");
			if ($result!=0){
				die "Failed to create target archive directory ${archive_path}...quitting";
			} else {
				print "Created target archive directory ${archive_path}$/";	
			}
		} else {
			print "Use existing target archive directory ${archive_path}$/"
		}
	} elsif ($filename ne "deploy.pl"){
		#check if target file exists
		if (-e $target_path){
			#archive it
			#fix archive path
			$archive_path = $filedir;
			$archive_path =~ s/$cur_dir//g;
			$archive_path = "${dest}${archive_path}/archive/${archive_file}";
			my $result = system("cp ${target_path} ${archive_path}");
			if ($result!=0){
				die "Failed to archive ${target_path} to ${archive_path}...quitting";
			} else {
				print "Archived original file ${target_path} to ${archive_path}$/";
			}
		}
		#copy new file to destination
		my $result = system("cp ${filepath} ${target_path}");
		if ($result!=0){
			die "Failed to copy ${filepath} to ${target_path}...quitting";
		} else {
			print "Copied ${filepath} to ${target_path}$/";
		}
		#upgrade file permissions
		$result = system("chmod 755 ${target_path}");
		if ($result!=0){
			die "Failed to change file permissions to 755 for ${target_path}...quitting";
		} else {
			print "Changed file permissions to 755 for ${target_path}$/";
		}
	}
}