rem	�g�p����DB���[�U�A�p�X���[�h�A�ڑ��������ύX����ꍇ�́A
rem	���L�L�q�������Q�l�ɂ��Ă��������B
rem	�y�L�q�����z
rem	sqlplus <�g�p����DB���[�U>/<�p�X���[�h>@<�l�b�g�T�[�r�X��> @terasoluna_functionsample_rich.sql

sqlplus functest/functest@10.68.255.230:1521/TERADB @create_table_job_control.sql
sqlplus functest/functest@10.68.255.230:1521/TERADB @create_table_user_test.sql
sqlplus functest/functest@10.68.255.230:1521/TERADB @create_table_user_test2.sql
sqlplus functest/functest@10.68.255.230:1521/TERADB @terasoluna_fruitshop.sql

call oracle_init_job_control.bat
call oracle_init_user_test.bat
call oracle_init_user_test2.bat

pause