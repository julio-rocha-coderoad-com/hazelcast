package com.hazelcast.dataset.impl.operations;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.query.Predicate;

import java.io.IOException;

import static com.hazelcast.dataset.impl.DataSetDataSerializerHook.COMPILE_PREDICATE;

public class CompilePredicateOperation extends DataStoreOperation {

    public Predicate predicate;
    private String compiledQueryUuid;

    public CompilePredicateOperation() {
    }

    public CompilePredicateOperation(String name, String compiledQueryUuid, Predicate predicate) {
        super(name);
        this.predicate = predicate;
        this.compiledQueryUuid = compiledQueryUuid;
    }

    @Override
    public int getId() {
        return COMPILE_PREDICATE;
    }

    @Override
    public void run() throws Exception {
        container.getRecordStore(getPartitionId()).compilePredicate(compiledQueryUuid, predicate);
    }

    @Override
    protected void writeInternal(ObjectDataOutput out) throws IOException {
        super.writeInternal(out);
        out.writeUTF(compiledQueryUuid);
        out.writeObject(predicate);
    }

    @Override
    protected void readInternal(ObjectDataInput in) throws IOException {
        super.readInternal(in);
        compiledQueryUuid = in.readUTF();
        predicate = in.readObject();
    }
}