/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.FileLineException} クラスのテスト。
 * <p>
 * <h4>【クラスの概要】</h4> ファイルアクセス機能で発生する例外をラップするRuntimeException
 * <p>
 * @author 奥田哲司
 * @see jp.terasoluna.fw.file.dao.FileLineException
 */
public class FileLineExceptionTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FileLineExceptionTest.class);
    }

    /**
     * 初期化処理を行う。
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        VMOUTUtil.initialize();
    }

    /**
     * 終了処理を行う。
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * @param name このテストケースの名前。
     */
    public FileLineExceptionTest(String name) {
        super(name);
    }

    /**
     * testFileLineExceptionException01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) e:not null<br>
     * Exceptionのインスタンス<br>
     * <br>
     * 期待値：(状態変化) columnName:null<br>
     * (状態変化) columnIndex:-1<br>
     * (状態変化) lineNo:-1<br>
     * (状態変化) FileLineException.cause: 引数eと等価のオブジェクト。<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタの引数で受け取ったExceptionをラップすることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionException01() throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        Exception exception = new Exception();

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(exception);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertNull(ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "columnIndex"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "lineNo"));
        assertSame(exception, fe.getCause());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(1, arguments.size());
        assertSame(exception, arguments.get(0));
    }

    /**
     * testFileLineExceptionString01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) message:not null<br>
     * 空のStringインスタンス<br>
     * <br>
     * 期待値：(状態変化) columnName:null<br>
     * (状態変化) columnIndex:-1<br>
     * (状態変化) lineNo:-1<br>
     * (状態変化) FileLineException.detailMessage: 引数messageと等価のオブジェクト。<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数に例外メッセージを設定する。<br>
     * 例外オブジェクト生成後にメッセージが格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionString01() throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        String message = new String();

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(message);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertNull(ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "columnIndex"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "lineNo"));
        assertEquals(message, fe.getMessage());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(1, arguments.size());
        assertSame(message, arguments.get(0));
    }

    /**
     * testFileLineExceptionStringException01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) message:not null<br>
     * 空のStringインスタンス<br>
     * (引数) exceotion:not null<br>
     * Exceptionのインスタンス<br>
     * <br>
     * 期待値：(状態変化) columnName:null<br>
     * (状態変化) columnIndex:-1<br>
     * (状態変化) lineNo:-1<br>
     * (状態変化) FileLineException.detailMessage: 引数messageと等価のオブジェクト。<br>
     * (状態変化) FileLineException.caouse: 引数exceptionと等価のオブジェクト<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数に例外メッセージを設定する。<br>
     * 例外オブジェクト生成後にメッセージが格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringException01() throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        String message = new String();
        Exception exception = new Exception();

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(message, exception);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertNull(ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "columnIndex"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "lineNo"));
        assertSame(message, fe.getMessage());
        assertSame(exception, fe.getCause());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
    }

    /**
     * testFileLineExceptionExceptionStringint01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) exception:not null<br>
     * Exceptionインスタンス<br>
     * (引数) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (引数) lineNo:1<br>
     * <br>
     * 期待値：(状態変化) this.colmnName:null<br>
     * (状態変化) this.columnIndex:-1<br>
     * (状態変化) this.lineNo:1<br>
     * (状態変化) FileLineException.cause: 引数exceptionと等価のオブジェクト<br>
     * (状態変化) FileLineException.fileName: 引数fileNameと等価オブジェクト<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数にファイル名を設定する。<br>
     * 例外オブジェクト生成後にファイル名が格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionExceptionStringint01() throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(exception, fileName,
                lineNo);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertNull(ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(-1, ReflectionTestUtils.getField(fe, "columnIndex"));
        assertEquals(1, ReflectionTestUtils.getField(fe, "lineNo"));
        assertSame(exception, fe.getCause());
        assertSame(fileName, ReflectionTestUtils.getField(fe, "fileName"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(exception, arguments.get(0));
        assertSame(fileName, arguments.get(1));
    }

    /**
     * testFileLineExceptionStringExceptionStringint01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) message:空のStringインスタンス<br>
     * (引数) e:Exceptionインスタンス<br>
     * (引数) fileName:空のStringインスタンス<br>
     * (引数) lineNo:1<br>
     * <br>
     * 期待値：(状態変化) FileLineException.cause: 引数eと等価のオブジェクト<br>
     * (状態変化) FileLineException.fileName: 引数fileNameと等価のオブジェクト<br>
     * (状態変化) FileLineException.detailMessage: 引数messageと等価のオブジェクト<br>
     * (状態変化) lineNo:引数lineNoと同じ値<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数にファイル名を設定する。<br>
     * 例外オブジェクト生成後にファイル名が格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringExceptionStringint01()
                                                                 throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertSame(exception, fe.getCause());
        assertSame(fileName, ReflectionTestUtils.getField(fe, "fileName"));
        assertSame(message, fe.getMessage());
        assertEquals(1, ReflectionTestUtils.getField(fe, "lineNo"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(3, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
        assertSame(fileName, arguments.get(2));
    }

    /**
     * testFileLineExceptionExceptionStringintStringint01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) exception:not null<br>
     * Exceptionインスタンス<br>
     * (引数) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (引数) lineNo:1<br>
     * (引数) columnName:not null<br>
     * 空のStringインスタンス<br>
     * (引数) columnIndex:1<br>
     * <br>
     * 期待値：(状態変化) FileLineException.cause: 引数exceptionと等価のオブジェクト<br>
     * (状態変化) FileLineException.fileName: 引数fileNameと等価のオブジェクト<br>
     * (状態変化) FileLineException.lineNo: 引数lineNoと同じ値<br>
     * (状態変化) FileLineException.columnName: 引数columnNameと等価のオブジェクト<br>
     * (状態変化) FileLineException.columnIndex: 引数columnIndexと同じ値<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数にファイル名を設定する。<br>
     * 例外オブジェクト生成後にファイル名が格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionExceptionStringintStringint01()
                                                                    throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(exception, fileName,
                lineNo, columnName, columnIndex);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertSame(exception, fe.getCause());
        assertEquals(fileName, ReflectionTestUtils.getField(fe, "fileName"));
        assertEquals(lineNo, ReflectionTestUtils.getField(fe, "lineNo"));
        assertEquals(columnName, ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(columnIndex, ReflectionTestUtils.getField(fe, "columnIndex"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(exception, arguments.get(0));
        assertSame(fileName, arguments.get(1));
    }

    /**
     * testFileLineExceptionStringExceptionStringintStringint01() <br>
     * <br>
     * (正常系) <br>
     * 観点：E <br>
     * <br>
     * 入力値：(引数) message:not null<br>
     * 空のStringインスタンス<br>
     * (引数) exception:not null<br>
     * Exceptionインスタンス<br>
     * (引数) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (引数) lineNo:1<br>
     * (引数) columnName:not null<br>
     * 空のStringインスタンス<br>
     * (引数) columnIndex:1<br>
     * <br>
     * 期待値：(状態変化) FileLineException.message: 引数messageと等価のオブジェクト<br>
     * (状態変化) FileLineException.cause: 引数exceptionと等価のオブジェクト<br>
     * (状態変化) FileLineException.fileName: 引数fileNameと等価のオブジェクト<br>
     * (状態変化) FileLineException.lineNo: 引数lineNoと同じ値<br>
     * (状態変化) FileLineException.columnName: 引数columnNameと等価のオブジェクト<br>
     * (状態変化) FileLineException.columnIndex: 引数columnIndexと同じ値<br>
     * (状態変化) FileException:1回呼ばれる。<br>
     * <br>
     * コンストラクタ引数にファイル名を設定する。<br>
     * 例外オブジェクト生成後にファイル名が格納されていることを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringExceptionStringintStringint01()
                                                                          throws Exception {
        // テスト対象のインスタンス化
        // コンストラクタなので不要

        // 引数の設定
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;

        // 前提条件の設定
        // なし

        // テスト実施
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // 返却値の確認
        // なし

        // 状態変化の確認
        assertSame(message, fe.getMessage());
        assertSame(exception, fe.getCause());
        assertEquals(fileName, ReflectionTestUtils.getField(fe, "fileName"));
        assertEquals(lineNo, ReflectionTestUtils.getField(fe, "lineNo"));
        assertEquals(columnName, ReflectionTestUtils.getField(fe, "columnName"));
        assertEquals(columnIndex, ReflectionTestUtils.getField(fe, "columnIndex"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(3, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
        assertSame(fileName, arguments.get(2));
    }

    /**
     * testGetColumnName01() <br>
     * <br>
     * (正常系) <br>
     * 観点：F <br>
     * <br>
     * 入力値：(状態) message:not null<br>
     * 空のStringインスタンス<br>
     * (状態) exception:not null<br>
     * Exceptionインスタンス<br>
     * (状態) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) lineNo:1<br>
     * (状態) columnName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) columnIndex:0<br>
     * <br>
     * 期待値：(戻り値) String:前提条件のcolumnNameと同一のオブジェクト<br>
     * <br>
     * columnNameのgetterメソッドが正しく値を取得することを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */

    public void testGetColumnName01() throws Exception {
        // テスト対象のインスタンス化
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // 引数の設定
        // なし

        // 前提条件の設定
        // なし

        // テスト実施
        String result = fe.getColumnName();

        // 返却値の確認
        assertSame(columnName, result);

        // 状態変化の確認
        // なし
    }

    /**
     * testGetLineNo01() <br>
     * <br>
     * (正常系) <br>
     * 観点：F <br>
     * <br>
     * 入力値：(状態) message:not null<br>
     * 空のStringインスタンス<br>
     * (状態) exception:not null<br>
     * Exceptionインスタンス<br>
     * (状態) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) lineNo:1<br>
     * (状態) columnName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) columnIndex:0<br>
     * <br>
     * 期待値：(戻り値) int:前提条件のlineNoと同じ値<br>
     * <br>
     * lineNoのgetterメソッドが正しく値を取得することを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */

    public void testGetLineNo01() throws Exception {
        // テスト対象のインスタンス化
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // 引数の設定
        // なし

        // 前提条件の設定
        // なし

        // テスト実施
        int result = fe.getLineNo();

        // 返却値の確認
        assertEquals(lineNo, result);

        // 状態変化の確認
        // なし
    }

    /**
     * testGetColumnIndex01() <br>
     * <br>
     * (正常系) <br>
     * 観点：F <br>
     * <br>
     * 入力値：(状態) message:not null<br>
     * 空のStringインスタンス<br>
     * (状態) exception:not null<br>
     * Exceptionインスタンス<br>
     * (状態) fileName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) lineNo:-1<br>
     * (状態) columnName:not null<br>
     * 空のStringインスタンス<br>
     * (状態) columnIndex:1<br>
     * <br>
     * 期待値：(戻り値) int:前提条件のcolumnIndexと同じ値<br>
     * <br>
     * 属性の値が正しく値を取得することを確認する。 <br>
     * @throws Exception このメソッドで発生した例外
     */

    public void testGetColumnIndex01() throws Exception {
        // テスト対象のインスタンス化
        FileLineException fe = new FileLineException(new String());

        // 引数の設定
        // なし

        // 前提条件の設定
        int columnIndex = 1;
        ReflectionTestUtils.setField(fe, "columnIndex", columnIndex);

        // テスト実施
        int result = fe.getColumnIndex();

        // 返却値の確認
        assertEquals(columnIndex, result);

        // 状態変化の確認
        // なし
    }

}
