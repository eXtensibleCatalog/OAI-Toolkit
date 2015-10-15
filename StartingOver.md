# Starting Over With a Fresh, New Repository #

You may at some point wish to start over with a fresh, new OAI Toolkit repository.  Perhaps you made a mistake in one of your configuration files, or something else.  In any case, here's how you can begin anew:

1 **Refresh the mysql database**

```
> mysql -uroot -p < sql/oai.sql
```

2 **Refresh the Lucene index**

```
> rm -rf lucene_index/*
```

3 **Re-run your load.sh script**

```
> ./load.sh
```