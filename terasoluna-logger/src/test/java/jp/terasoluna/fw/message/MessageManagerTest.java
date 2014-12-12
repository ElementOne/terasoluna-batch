package jp.terasoluna.fw.message;

import static org.junit.Assert.*;
import static jp.terasoluna.fw.ex.unit.util.AssertUtils.*;

import java.net.URL;
import java.util.Arrays;

import jp.terasoluna.fw.message.execption.MessageRuntimeException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageManagerTest {
    private ClassLoader currentClassLoader;

    /**
     * �O����
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        currentClassLoader = Thread.currentThread().getContextClassLoader();
    }

    /**
     * �㏈��
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        Thread.currentThread().setContextClassLoader(currentClassLoader);
    }

    /**
     * �R���X�g���N�^�̎����y01:����n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C���ɉ����ݒ肳��Ă��Ȃ�
     * 
     * �m�F���ځF
     * �E���b�Z�[�WID�̃t�H�[�}�b�g��[%s]
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g����
     * �E��O�X���[�t���O��false
     * </pre>
     */
    @Test
    public void testConstructor01() {
        // �ݒ�̂Ȃ��v���p�e�B�t�@�C��
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger01.properties");
        assertEquals("[%s] ", manager.messageIdFormat);
        assertCollectionEmpty(manager.basenames);
        assertFalse(manager.throwIfResourceNotFound);
    }

    /**
     * �R���X�g���N�^�̎����y02:����n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C���Ɋe�v�f�̒l���ݒ肳��Ă���
     * 
     * �m�F���ځF
     * �E���b�Z�[�WID�̃t�H�[�}�b�g��[%s]
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g�ɑΏۃt�@�C�������ꌏ�擾����Ă���
     * �E��O�X���[�t���O��true
     * </pre>
     */
    @Test
    public void testConstructor02() {
        // message.id.format��message.basename���ݒ肳��Ă���Athrow.if.resource.not.found��true
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger02.properties");
        assertEquals("|%s| ", manager.messageIdFormat);
        assertCollectionEquals(
                Arrays.asList("jp/terasoluna/fw/message/log-messages2"),
                manager.basenames);
        assertTrue(manager.throwIfResourceNotFound);
    }

    /**
     * �R���X�g���N�^�̎����y03:����n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C����message.basename�������ݒ肳��Ă���
     * 
     * �m�F���ځF
     * �E���b�Z�[�WID�̃t�H�[�}�b�g��[%s]
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g�ɑΏۃt�@�C�������������擾����Ă���
     * �E��O�X���[�t���O��false
     * </pre>
     */
    @Test
    public void testConstructor03() {
        // message.basename�������ݒ肳��Ă���Athrow.if.resource.not.found��false
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger03.properties");
        assertEquals("[%s] ", manager.messageIdFormat);
        assertCollectionEquals(Arrays.asList(
                "jp/terasoluna/fw/message/log-messages3-1",
                "jp/terasoluna/fw/message/log-messages3-2"), manager.basenames);
        assertFalse(manager.throwIfResourceNotFound);
    }

    /**
     * �R���X�g���N�^�̎����y04:����n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C�����N���X���[�_��ɕ������݂���
     * 
     * �m�F���ځF
     * �E���b�Z�[�WID�̃t�H�[�}�b�g��[%s]
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g�ɑΏۃt�@�C�������������擾����Ă���
     * �E��O�X���[�t���O��false
     * </pre>
     */
    @Test
    public void testConstructor04() {
        // �Y������v���p�e�B�t�@�C�����N���X���[�_��ɕ������݂���
        MockClassLoader cl = new MockClassLoader();
        cl.addMapping("META-INF/terasoluna-logger.properties",
                getResource("terasoluna-logger01.properties"),
                getResource("terasoluna-logger02.properties"),
                getResource("terasoluna-logger03.properties"));
        // �N���X���[�_�̍����ւ�
        Thread.currentThread().setContextClassLoader(cl);

        MessageManager manager = new MessageManager(
                "META-INF/terasoluna-logger.properties");
        assertEquals("[%s] ", manager.messageIdFormat);
        assertCollectionEquals(Arrays.asList(
                "jp/terasoluna/fw/message/log-messages2",
                "jp/terasoluna/fw/message/log-messages3-1",
                "jp/terasoluna/fw/message/log-messages3-2"), manager.basenames);
        assertFalse(manager.throwIfResourceNotFound);
    }

    /**
     * �R���X�g���N�^�̎����y05:����n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C�����N���X���[�_��ɕ������݂���
     * 
     * �m�F���ځF
     * �E���b�Z�[�WID�̃t�H�[�}�b�g��[%s]
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g�ɑΏۃt�@�C�������������擾����Ă���
     * �E��O�X���[�t���O��true�i�D��x�̍����v���p�e�B�t�@�C���̒l�ɂȂ��Ă��邩�m�F����j
     * </pre>
     */
    @Test
    public void testConstructor05() {
        MockClassLoader cl = new MockClassLoader();
        cl.addMapping("META-INF/terasoluna-logger.properties",
                getResource("terasoluna-logger02.properties"),
                getResource("terasoluna-logger01.properties"),
                getResource("terasoluna-logger03.properties"));
        // �N���X���[�_�̍����ւ�
        Thread.currentThread().setContextClassLoader(cl);

        MessageManager manager = new MessageManager(
                "META-INF/terasoluna-logger.properties");
        assertEquals("|%s| ", manager.messageIdFormat);
        assertCollectionEquals(Arrays.asList(
                "jp/terasoluna/fw/message/log-messages2",
                "jp/terasoluna/fw/message/log-messages3-1",
                "jp/terasoluna/fw/message/log-messages3-2"), manager.basenames);
        assertTrue(manager.throwIfResourceNotFound);
    }

    /**
     * �R���X�g���N�^�̎����y06:�ُ�n�z
     * 
     * <pre>
     * ���O�����F
     * �E�s���ȃN���X���[�_��ݒ肷��
     * 
     * �m�F���ځF
     * �E�R���X�g���N�^�̌Ăяo���ɂ��AMessageRuntimeException�̔������m�F
     */
    @Test
    public void testConstructor06() {
        Thread.currentThread().setContextClassLoader(new BadClassLoader());
        try {
            new MessageManager("");
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("java.lang.RuntimeException: hoge", e.getMessage());
            assertEquals(MessageRuntimeException.class, e.getClass());
        }
    }

    @Test
    public void testConstructor07() throws Exception {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger05.properties");
        assertEquals(SampleMessageFormatter01.class,
                manager.messageFormatter.getClass());
    }

    @Test
    public void testConstructor08() throws Exception {
        try {
            new MessageManager(
                    "jp/terasoluna/fw/message/terasoluna-logger06.properties");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(MessageRuntimeException.class, e.getClass());
            assertEquals(ClassNotFoundException.class, e.getCause().getClass());
        }
    }

    protected static URL getResource(String name) {
        return MessageManager.class.getClassLoader().getResource(
                "jp/terasoluna/fw/message/" + name);
    }

    /**
     * getMessage���\�b�h�����y01:����n�z getMessagePattern���\�b�h�̓�����m�F����
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ރv���p�e�B�t�@�C�������݂���
     * 
     * �m�F���ځF
     * �E�w�肵�����b�Z�[�WID�����݂��Ȃ��ꍇ�Anull�l���Ԃ�
     * �E�w�肵�����b�Z�[�WID��null�l�̏ꍇ�Anull�l���Ԃ�
     * </pre>
     */
    @Test
    public void testGetMessage01() {
        MessageManager manager = new MessageManager(
                "META-INF/terasoluna-logger.properties");
        assertNull(manager.getMessagePattern("hoge", null));
        assertNull(manager.getMessagePattern(null, null));
    }

    /**
     * getMessage���\�b�h�����y02:�ُ�n�z getMessagePattern���\�b�h�̗�O�������m�F����
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ރv���p�e�B�t�@�C�������݂���
     * �E��O�X���[�t���O��true
     * 
     * �m�F���ځF
     * �E�w�肵�����b�Z�[�WID�����݂��Ȃ��ꍇ�AMessageRuntimeException���Ԃ�
     * </pre>
     */
    @Test
    public void testGetMessage02() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger02.properties");
        try {
            manager.getMessagePattern("hoge", null);
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("key[hoge] is not found", e.getMessage());
            assertEquals(MessageRuntimeException.class, e.getClass());
        }
    }

    /**
     * getMessage���\�b�h�����y03:����n�z �v���p�e�B�t�@�C���ɑ��݂��郁�b�Z�[�W���擾
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ރv���p�e�B�t�@�C���ɊY�����郁�b�Z�[�W���ݒ肳��Ă���
     * 
     * �m�F���ځF
     * �E�v���p�e�B�t�@�C��������҂��郁�b�Z�[�W���擾����Ă���
     * </pre>
     */
    @Test
    public void testGetMessage03() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger04.properties");
        assertEquals("[message01] ���b�Z�[�W01",
                manager.getMessage(true, "message01"));

    }

    /**
     * getMessage���\�b�h�����y04:����n�z �v���p�e�B�t�@�C���ɑ��݂��郁�b�Z�[�W���擾
     * 
     * <pre>
     * ���O�����F
     * �E���\�[�X�����̏�����getMessage���\�b�h���Ăяo���Ă���
     * 
     * �m�F���ځF
     * �E�ݒ肵���u���p�����[�^������ɏo�͂���Ă���
     * </pre>
     */
    @Test
    public void testGetMessage04() {
        MessageManager manager = new MessageManager("");
        assertEquals("���b�Z�[�W01 �u��������01=hoge,�u��������02=foo", manager.getMessage(
                false, "���b�Z�[�W01 �u��������01={0},�u��������02={1}", null, "hoge", "foo"));
    }

    /**
     * getMessage���\�b�h�����y05:�ُ�n�z
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C���ɕs���Ȓu���p�����[�^���ݒ肳�ꂽ���b�Z�[�W�����݂���
     * �E��O�X���[�t���O��true
     * 
     * �m�F���ځF
     * �E�s���u���p�����[�^��ݒ肵�����b�Z�[�W�̌Ăяo���ɂ��AMessageRuntimeException���Ԃ�
     * </pre>
     */
    @Test
    public void testGetMessage05() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger04.properties");

        try {
            manager.getMessage(true, "message02");
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(
                    "message pattern is illeagal. pattern=���b�Z�[�W02{a}] logId=message02",
                    e.getMessage());
            assertEquals(MessageRuntimeException.class, e.getClass());
        }

    }

    /**
     * getStringOrNull���\�b�h�����y01:����n�z
     * 
     * <pre>
     * ���O�����F
     * �EgetStringOrNull���\�b�h�̈����ł���ResourceBundle�̒l��null�ł���
     * 
     * �m�F���ځF
     * �Enull�l���Ԃ�
     * </pre>
     */
    @Test
    public void testGetStringOrNull01() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger02.properties");
        assertNull(manager.getStringOrNull(null, null));
    }

    /**
     * getStringOrNull���\�b�h�����y02:�ُ�n�z
     * 
     * <pre>
     * ���O�����F
     * �EgetStringOrNull���\�b�h�̈����ł���String�^�ϐ�key�̒l��null�ł���
     * �E��O�X���[�t���O��true
     * 
     * �m�F���ځF
     * �EMessageRuntimeException���Ԃ邱��
     * </pre>
     */
    @Test
    public void testGetStringOrNull02() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger02.properties");
        try {
            manager.getMessagePattern(null, null);
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("key is null", e.getMessage());
            assertEquals(MessageRuntimeException.class, e.getClass());
        }
    }

    /**
     * getResourceBundle���\�b�h�����y01:�ُ�n�z getStringOrNull�̗�O�������m�F����
     * 
     * <pre>
     * ���O�����F
     * �E�ǂݍ��ݑΏۂ̃v���p�e�B�t�@�C�������݂���
     * �E���݂��Ȃ��v���p�e�B�t�@�C�����w�肵��getResourceBundle���\�b�h���Ăяo��
     * �E��O�X���[�t���O��true
     * 
     * �m�F���ځF
     * �EgetResourceBundle���\�b�h����MessageRuntimeException���Ԃ�
     * </pre>
     */
    @Test
    public void testGetResourceBundle() {
        MessageManager manager = new MessageManager(
                "jp/terasoluna/fw/message/terasoluna-logger02.properties");
        try {
            manager.getResourceBundle("META-INF/hoge.properties", null);
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("resource[META-INF/hoge.properties] is not found",
                    e.getMessage());
            assertEquals(MessageRuntimeException.class, e.getClass());
        }
    }

    /**
     * getClassLoader���\�b�h�����y01:����n�z
     * 
     * <pre>
     * �m�F���ځF
     * �E�X���b�h�ɐݒ肳�ꂽ�R���e�L�X�g�N���X���[�_��getClassLoader���\�b�h�ɂ��擾�ł��邱�Ƃ��m�F
     * </pre>
     */
    @Test
    public void testGetClassLoader01() {
        assertSame(Thread.currentThread().getContextClassLoader(),
                MessageManager.getClassLoader());
    }

    /**
     * getClassLoader���\�b�h�����y02:����n�z
     * 
     * <pre>
     * �m�F���ځF
     * �E�X���b�h�ɐݒ肳�ꂽ�R���e�L�X�g�N���X���[�_�ɑ΂��Anull�l��ݒ肷��
     * �EMessageManager.getClassLoader�̌��ʂ�MessageManager.class�����[�h�����N���X���[�_�ɂȂ��Ă邱�Ƃ��m�F
     * </pre>
     */
    @Test
    public void testGetClassLoader02() {
        Thread.currentThread().setContextClassLoader(null);
        assertSame(MessageManager.class.getClassLoader(),
                MessageManager.getClassLoader());
    }
}
