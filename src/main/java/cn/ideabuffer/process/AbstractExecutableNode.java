package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {


    public AbstractExecutableNode(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }
}
