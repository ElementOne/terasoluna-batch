package jp.terasoluna.fw.batch.executor;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class PlatformTransactionManagerStub01 implements PlatformTransactionManager {

    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        throw new TransactionException("�g�����U�N�V�����J�n�m�F"){
            /**
             * 
             */
            private static final long serialVersionUID = 5076590482287633904L;};
    }

    public void commit(TransactionStatus status) throws TransactionException {
        throw new TransactionException("�R�~�b�g�m�F") {
            /**
             * 
             */
            private static final long serialVersionUID = -4070645657400091636L;
        };
    }

    public void rollback(TransactionStatus status) throws TransactionException {
        throw new TransactionException("���[���o�b�N�m�F") {
            /**
             * 
             */
            private static final long serialVersionUID = -9116878319303004981L;
        };
    }

}
