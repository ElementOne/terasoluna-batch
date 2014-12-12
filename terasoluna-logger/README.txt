�� TLogger...�ėp���K�[

���̃��K�[�o�R�Ń��O�o�͂��s���ƈȉ��̗��_������܂��B

�E���O���b�Z�[�W���v���p�e�B�t�@�C���ŊǗ��ł���
�E���O�o�̓��\�b�h�����OID�ƒu���p�����[�^�ŌĂяo����
�E�u���p�����[�^�͉ϒ��z��œn����
�E�o�͂���郍�O�Ƀ��OID�������Ŗ��ߍ��܂��
�E���O���x���m�F��if����(�����̏ꍇ)�s�v
�E���W���[�����Ƀ��b�Z�[�W�v���p�e�B�t�@�C�����Ǘ��ł���
�E���b�Z�[�W�v���p�e�B�t�@�C���Ȃ��ł����p�ł���

���� ���K�[�擾

���K�[�̎擾�͑��̃��C�u�����Ƃقړ����ł��B
private static final TLogger LOGGER = TLogger.getLogger(XXX.class);
�܂���
private static final TLogger LOGGER = TLogger.getLogger("Hoge");

���� ���O�o��

���̂悤�ȃ��O���b�Z�[�W�v���p�e�B�t�@�C��������ꍇ

DEB001=debug message
ERR001=error message
���̃��b�Z�[�W���o�͂���ɂ�

LOGGER.debug("DEB001"); 
LOGGER.error("ERR001");
�̂悤�Ƀ��O���x���ɉ��������\�b�h�Ƀ��OID��n���Ď��s���܂��B �o�̓��b�Z�[�W��

[DEB001] debug message
[ERR001] error message
�ƂȂ�܂��B���b�Z�[�W�̑O��[���OID]�������ŕt���܂��B

���O���x����

FATAL
ERROR
WARN
INFO
DEBUG
TRACE
������܂��Blog(String logId)���\�b�h���g�p����ƁA���OID�̈ꕶ���ڂ����ă��O���x���𔻒f���܂��B

LOGGER.log("ERR001"); // ���O���x����ERROR

���� �p�����[�^�u��

�o�͂��郍�O���b�Z�[�W���쐬����ۂɁAjava.text.MessageFormat���g�p���Ă��܂��B�u���p�����[�^���ϒ��z��œn�����Ƃ��ł��܂��B

DEB002={0} is {1}.
�Ƃ�����`������ꍇ�A

LOGGER.debug("DEB002", "hoge", "foo");
�����s����Əo�̓��b�Z�[�W��

[DEB002] hoge is foo.
�ƂȂ�܂��B

�����Ń��b�Z�[�W��������쐬����ۂɃ��O���x���̃`�F�b�N���s���Ă���̂ŁA

if (LOGGER.isDebugEnabled()) {
    LOGGER.debug("DEB002", "hoge", "foo");
}
�Ƃ���if���������K�v������܂���B(�������A�p�����[�^���쐬����ۂɃR�X�g�̂����郁�\�b�h���Ăяo���Ă���ꍇ��if���������Ė����I�Ƀ��O���x���`�F�b�N���s���Ă��������B)

���� ���b�Z�[�W�v���p�e�B�t�@�C���ɂȂ����b�Z�[�W�̏o��

���b�Z�[�W�v���p�e�B�t�@�C�����̃��OID��n�����ɁA���ڃ��b�Z�[�W��n�����@������܂��B��1������false ��ݒ肵�A��2�����Ƀ��b�Z�[�W�{���𒼐ڋL�q�ł��܂��B��3�����ȍ~�͒u���p�����[�^�ł��B���̏ꍇ�͓��R���OID�͏o�͂���܂���B

LOGGER.warn(false, "warn!!");
LOGGER.info(false, "Hello {0}!", "World");
�o�̓��b�Z�[�W��

warn!!
Hello World!
�ƂȂ�܂��B

���� �ݒ�t�@�C��

�N���X�p�X������META-INF�f�B���N�g����terasoluna-logger.properties���쐬���Ă��������B

������ ���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[���ݒ�

terasoluna-logger.properties��message.basename�L�[�Ƀ��b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����N���X�p�X����(FQCN)�Őݒ肵�Ă��������B
java.util.ResourceBundle�œǂݍ��ނ̂ŁA���ۉ��ɑΉ����Ă��܂��B

message.basename=hoge
�Ə����ƃN���X�p�X������hoge.properties���ǂݍ��܂�܂��B

message.basename=hoge,foo,bar
�̂悤�ɔ��p�J���}��؂�Őݒ肷��ƑS�Ă�ǂݍ��݂܂��B

META-INF/terasoluna-logger.properies��message.basename�̓��W���[�����ɐݒ�ł��܂��B
���K�[�͑S�Ẵ��W���[��(jar)�����Amessage.basename�̒l���}�[�W���ă��b�Z�[�W���擾���܂��B

����ɂ��A���W���[�����Ƀ��O���b�Z�[�W���Ǘ����邱�Ƃ��ł��܂��B

������ �o�̓��OID�t�H�[�}�b�g�ݒ�

���O�o�͎��Ɏ����ŕt������郍�OID�̃t�H�[�}�b�g��ݒ�ł��܂��B
message.id.format�L�[��java.lang.String.format()�̃t�H�[�}�b�g�`���Őݒ肵�Ă��������B���OID��������Ƃ��ēn����܂��B

�ݒ肵�Ȃ��ꍇ�́u[%s]�v���f�t�H���g�l�Ƃ��Ďg�p����܂��B

message.id.format=[%-8s]
�̂悤�ɐݒ肷��ƁA���W���[���ԂňقȂ钷���̃��OID�����񂹂ő����ďo�͂ł��܂��B

���̐ݒ�l�̓��W���[�����ɊǗ����邱�Ƃ͂ł��܂���B
�N���X���[�_�̓ǂݍ��ݗD��x����ԍ���terasoluna-logger.properties�̒l�����f����܂��B
 (�ʏ�A�A�v�����̐ݒ�ƂȂ�܂��B)


�� TException,TRuntimeException...�ėp��O

���̗�O�N���X��p����ƈȉ��̗��_������܂��B

�E��O���b�Z�[�W���v���p�e�B�t�@�C���ŊǗ��ł���
�E��O���b�Z�[�W���O���b�Z�[�WID�ƒu���p�����[�^�ō쐬�ł���

���K�[�Ƃقړ��l�ł��B
���� ��O�N���X�쐬

���b�Z�[�W�t�@�C����
ERR01= {0} was occured!
�ƒ�`����Ă���ꍇ�A

TException e = new TException("ERR01", "something");

���쐬����ƁA��O���b�Z�[�W��
[ERR01] somethins was occured!
�ɂȂ�܂��B

���� �ݒ�t�@�C��

�N���X�p�X������META-INF�f�B���N�g����terasoluna-exception.properties���쐬���Ă��������B
���̑��͔ėp���K�[�̏ꍇ�Ɠ����ł��B

