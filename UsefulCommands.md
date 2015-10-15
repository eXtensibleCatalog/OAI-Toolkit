# Useful Commands #

The OAI-PMH protocol is the standard used to move metadata from the OAI Toolkit into other XC applications, such as the Metadata Services Toolkit.

1 **Removing (Control M ^M) characters from a Linux file transferred from Windows:**

> If you have transferred a file from a Windows to a Linux machine, and if it consists of Control-M characters, you can run this command to to open it in a vi editor:

```
>vi filename.sh
  	Inside the editor type this
  	:%s/(Control-V)(Control-M)//g     (It would look like :%s/^M//g)
```

2 **To make a file executable inside Linux machine:**

```
> chmod +x *.sh 
```

3 **Running a process in the background on Linux machines**

```
> command &
```

4 **Nohup:** Nohup is a command in Unix/Linux to run another command while suppressing the action of an HUP (Hangup) Signal, enabling the command to keep running after the user who issues the command has logged out. The screen output gets logged in another file called nohup.out. “Process” here means any command you are executing:

```
> nohup process &
> exit  
```