/**
 * 
 */
package jp.terasoluna.fw.file.dao.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.junit.Test;

/**
 * @author btkamaguchit
 */
public class LineFeed0LineReaderTest extends TestCase {

    /**
     * ����<br>
     * @throws Exception
     */
    @Test
    public void testLineFeed0LineReader01() throws Exception {
        // �O����(����)
        String str = "123456789012345678901234567890";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = 10;

        // �e�X�g���{
        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // ����
        assertNotNull(reader);
        assertSame(reader, UTUtil.getPrivateField(target, "reader"));
        assertEquals(encoding, UTUtil.getPrivateField(target, "fileEncoding"));
        assertEquals(totalBytes, UTUtil.getPrivateField(target, "totalBytes"));
    }

    /**
     * �ُ�<br>
     * reader : null
     * @throws Exception
     */
    @Test
    public void testLineFeed0LineReader02() throws Exception {
        // �O����(����)
        Reader reader = null;
        String encoding = "UTF-8";
        int totalBytes = 10;

        // �e�X�g���{
        try {
            new LineFeed0LineReader(reader, encoding, totalBytes);
            fail();
        } catch (Exception e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("reader is required.", e.getMessage());
        }
    }

    /**
     * �ُ�<br>
     * encoding : null
     * @throws Exception
     */
    @Test
    public void testLineFeed0LineReader03() throws Exception {
        // �O����(����)
        String str = "123456789012345678901234567890";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = null;
        int totalBytes = 10;

        // �e�X�g���{
        try {
            new LineFeed0LineReader(reader, encoding, totalBytes);
            fail();
        } catch (Exception e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("fileEncoding is required.", e.getMessage());
        }
    }

    /**
     * �ُ�<br>
     * totalBytes < 0
     * @throws Exception
     */
    @Test
    public void testLineFeed0LineReader04() throws Exception {
        // �O����(����)
        String str = "123456789012345678901234567890";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = -1;

        // �e�X�g���{
        try {
            new LineFeed0LineReader(reader, encoding, totalBytes);
            fail();
        } catch (Exception e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("totalBytes is larger than 0.", e.getMessage());
        }
    }

    /**
     * ����<br>
     * @throws Exception
     */
    @Test
    public void testReadLine01() throws Exception {
        // �O����(����)
        String str = "111122223333444455556";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "1111");
        assertEquals(target.readLine(), "2222");
        assertEquals(target.readLine(), "3333");
        assertEquals(target.readLine(), "4444");
        assertEquals(target.readLine(), "5555");
        assertEquals(target.readLine(), "6");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * @throws Exception
     */
    @Test
    public void testReadLine02() throws Exception {
        // �O����(����)
        String str = "11112222333344445555";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "1111");
        assertEquals(target.readLine(), "2222");
        assertEquals(target.readLine(), "3333");
        assertEquals(target.readLine(), "4444");
        assertEquals(target.readLine(), "5555");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : UTF-8
     * @throws Exception
     */
    @Test
    public void testReadLine03() throws Exception {
        // �O����(����)
        String str = "1��2���R���S���܂�";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "1��");
        assertEquals(target.readLine(), "2��");
        assertEquals(target.readLine(), "�R��");
        assertEquals(target.readLine(), "�S��");
        assertEquals(target.readLine(), "�܂�");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : UTF-8
     * @throws Exception
     */
    @Test
    public void testReadLine04() throws Exception {
        // �O����(����)
        String str = "\r\r\n\n\\\"\"\".,/@<>";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "UTF-8";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "\r\r\n\n");
        assertEquals(target.readLine(), "\\\"\"\"");
        assertEquals(target.readLine(), ".,/@");
        assertEquals(target.readLine(), "<>");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : Shift-JIS
     * @throws Exception
     */
    @Test
    public void testReadLine05() throws Exception {
        // �O����(����)
        String str = "1��12��2�R���S���܂�";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "Shift-JIS";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "1��1");
        assertEquals(target.readLine(), "2��2");
        assertEquals(target.readLine(), "�R��");
        assertEquals(target.readLine(), "�S��");
        assertEquals(target.readLine(), "�܂�");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : Shift-JIS
     * @throws Exception
     */
    @Test
    public void testReadLine06() throws Exception {
        // �O����(����)
        String str = "\r\r\n\n\\\"\"\".,/@<>";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "Shift-JIS";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "\r\r\n\n");
        assertEquals(target.readLine(), "\\\"\"\"");
        assertEquals(target.readLine(), ".,/@");
        assertEquals(target.readLine(), "<>");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : EUC-JP
     * @throws Exception
     */
    @Test
    public void testReadLine07() throws Exception {
        // �O����(����)
        String str = "1��12��2�R���S���܂�";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "EUC-JP";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "1��1");
        assertEquals(target.readLine(), "2��2");
        assertEquals(target.readLine(), "�R��");
        assertEquals(target.readLine(), "�S��");
        assertEquals(target.readLine(), "�܂�");
        assertEquals(target.readLine(), "");
    }

    /**
     * ����<br>
     * encoding : EUC-JP
     * @throws Exception
     */
    @Test
    public void testReadLine08() throws Exception {
        // �O����(����)
        String str = "\r\r\n\n\\\"\"\".,/@<>";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "EUC-JP";
        int totalBytes = 4;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);

        // �e�X�g���{
        // ����
        assertEquals(target.readLine(), "\r\r\n\n");
        assertEquals(target.readLine(), "\\\"\"\"");
        assertEquals(target.readLine(), ".,/@");
        assertEquals(target.readLine(), "<>");
        assertEquals(target.readLine(), "");
    }

    /**
     * �ُ�<br>
     * encoding : �s���l
     * @throws Exception
     */
    @Test
    public void testReadLine09() throws Exception {
        // �O����(����)
        String str = "123456789012345678901234567890";
        Reader reader = new BufferedReader(new StringReader(str));
        String encoding = "";
        int totalBytes = 10;

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);
        // �e�X�g���{
        try {
            target.readLine();
            fail();
        } catch (Exception e) {
            // ����
            assertTrue(e instanceof FileException);
            assertEquals("Reader control operation was failed.", e.getMessage());
            assertTrue(e.getCause() instanceof IOException);
        }
    }

    /**
     * �ُ�<br>
     * Reader#read() : IOException
     * @throws Exception
     */
    @Test
    public void testReadLine10() throws Exception {
        // �O����(����)
        String encoding = "UTF-8";
        int totalBytes = 10;

        // Mock�쐬
        Reader reader = EasyMock.createMock(Reader.class);
        EasyMock.expect(reader.read()).andReturn(null).andThrow(
                new IOException());
        EasyMock.replay(reader);

        LineFeed0LineReader target = new LineFeed0LineReader(reader, encoding,
                totalBytes);
        // �e�X�g���{
        try {
            target.readLine();
            fail();
        } catch (Exception e) {
            // ����
            assertTrue(e instanceof FileException);
            assertEquals("Reader control operation was failed.", e.getMessage());
            assertTrue(e.getCause() instanceof IOException);
        }
    }
}
