rem	�g�p����DB���[�U�A�p�X���[�h�A�ڑ��������ύX����ꍇ�́A
rem	���L�L�q�������Q�l�ɂ��Ă��������B
rem	�y�L�q�����z
rem	sqlplus <�g�p����DB���[�U>/<�p�X���[�h>@<�l�b�g�T�[�r�X��> @terasoluna_functionsample_thin.sql

sqlplus functest/functest@10.68.255.230:1521/TERADB @truncate_user_test.sql
sqlplus functest/functest@10.68.255.230:1521/TERADB @insert_user_test.sql
REM sqlplus functest/functest@TERADB @delete_user_test.sql

pause