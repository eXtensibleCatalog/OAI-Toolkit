set termout OFF
set echo OFF
set verify OFF
set heading OFF
set feedback OFF
set pause OFF
set pages 0
spool &1
select distinct Z00R_doc_number || '&2' as bibno from z00r a where not exists (select b.z00r_doc_number from z00r  b where b.z00r_doc_number = a.z00r_doc_number and b.z00r_field_code = 'DEL') and not exists (select c.z00r_doc_number from z00r  c where c.z00r_doc_number = a.z00r_doc_number and c.z00r_field_code like '902%' and (upper(z00r_text)  like '%SUPPRESS%'));
spool off
exit
