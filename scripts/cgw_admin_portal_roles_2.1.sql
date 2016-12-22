
declare
  type ROLE_ARRAY is varray(5) of VARCHAR2(50);
  
  roles                     ROLE_ARRAY;
  start_seq_id              NUMBER;
  seq_update_value          NUMBER;
  v_code                    NUMBER;
  v_errm                    VARCHAR2(64);
  cnt                       NUMBER;
begin
  DBMS_OUTPUT.put_line('Start');

  roles := ROLE_ARRAY(
   'CGWADMIN::MSISDN_RANGE_SEARCH',
   'CGWADMIN::MSISDN_RANGE_CREATE',
   'CGWADMIN::MSISDN_RANGE_EDIT',
   'CGWADMIN::MSISDN_RANGE_DELETE',
   'CGWADMIN::MSISDN_INDIVIDUAL_MSISDN_CREATE'
  );

  DBMS_OUTPUT.put_line('Roles assigned');

  SELECT  max_key INTO start_seq_id
    FROM    ojb_hl_seq
    WHERE   tablename = 'SEQ_SECURITY_PRINCIPAL';
    
    DBMS_OUTPUT.put_line('Start of ID sequence: ' || start_seq_id);
    
    seq_update_value := (floor(roles.COUNT / 20) + 1) * 20;
    
    DBMS_OUTPUT.put_line('Sequence will be updated by ' || seq_update_value);
    
    UPDATE  ojb_hl_seq
    SET     max_key = max_key + seq_update_value
    WHERE   tablename = 'SEQ_SECURITY_PRINCIPAL';
    
    DBMS_OUTPUT.put_line('Sequence updated');
    
    FOR i IN 1..roles.COUNT LOOP
    
      -- check if role already exists
      SELECT COUNT(1)
        INTO cnt
        FROM security_principal 
        WHERE classname = 'org.apache.jetspeed.security.InternalRolePrincipalImpl'
          AND full_path = '/role/'||roles(i);
      
      IF cnt > 0 THEN
        DBMS_OUTPUT.put_line('Role '|| roles(i) ||' already exists');
      ELSE
        DBMS_OUTPUT.put_line('Inserting role ' || roles(i));

        Insert into SECURITY_PRINCIPAL (
          PRINCIPAL_ID, 
          CLASSNAME, 
          IS_MAPPING_ONLY, 
          IS_ENABLED, 
          FULL_PATH, 
          CREATION_DATE, 
          MODIFIED_DATE) 
        values (
          i + start_seq_id - 1, 
          'org.apache.jetspeed.security.InternalRolePrincipalImpl', 
          0, 
          1, 
          '/role/' || roles(i), 
          sysdate,
          sysdate);
      END IF;
    END LOOP;
    
    DBMS_OUTPUT.put_line('Success');
    
    commit;
exception
when others then
    rollback;
    
    v_code := SQLCODE;
    v_errm := SUBSTR(SQLERRM, 1 , 64);
    DBMS_OUTPUT.PUT_LINE('Error: ' || v_code || ' - ' || v_errm);
end;
/
