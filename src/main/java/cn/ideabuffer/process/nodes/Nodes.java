package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.aggregate.DefaultUnitAggregatableNode;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Nodes {

    private Nodes() {
        throw new IllegalStateException("Utility class");
    }

    public static NodeGroup newGroup() {
        return new NodeGroup();
    }

    public static ParallelBranchNode newParallelBranchNode() {
        return new DefaultParallelBranchNode();
    }

    public static ParallelBranchNode newParallelBranchNode(List<BranchNode> branches) {
        return new DefaultParallelBranchNode(branches);
    }

    public static ParallelBranchNode newParallelBranchNode(boolean parallel) {
        return new DefaultParallelBranchNode(parallel);
    }

    public static ParallelBranchNode newParallelBranchNode(@NotNull Executor executor) {
        return new DefaultParallelBranchNode(true, executor);
    }

    public static ParallelBranchNode newParallelBranchNode(@NotNull Executor executor, Rule rule) {
        return new DefaultParallelBranchNode(true, rule, executor, null);
    }

    public static ParallelBranchNode newParallelBranchNode(@NotNull Executor executor, Rule rule,
        ExceptionHandler handler) {
        return new DefaultParallelBranchNode(true, rule, executor, handler);
    }

    public static <R> UnitAggregatableNode<R> newUnitAggregatableNode() {
        return new DefaultUnitAggregatableNode<>();
    }

    public static IfWhen newIf(Rule rule) {
        return new IfWhen(rule);
    }

    public static WhileWhen newWhile(Rule rule) {
        return new WhileWhen(rule);
    }

    public static DoWhileWhen newDoWhile(Rule rule) {
        return new DoWhileWhen(rule);
    }

    public static TryCatchFinally newTry(BranchNode branch) {
        return new TryCatchFinally(branch);
    }

    public static TryCatchFinally newTry(ExecutableNode... nodes) {
        return new TryCatchFinally(new DefaultBranchNode(nodes));
    }

    public static class IfWhen {

        private Rule rule;

        IfWhen(Rule rule) {
            this.rule = rule;
        }

        public IfWhenBuilder then(BranchNode branch) {
            return new IfWhenBuilder(rule, branch);
        }

        public IfWhenBuilder then(ExecutableNode... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public class IfWhenBuilder {

            private Rule rule;

            private BranchNode thenBranch;

            IfWhenBuilder(Rule rule, BranchNode thenBranch) {
                this.rule = rule;
                this.thenBranch = thenBranch;
            }

            public IfConditionNode otherwise(BranchNode branch) {
                return new IfConditionNode(rule, thenBranch, branch);
            }

            public IfConditionNode otherwise(ExecutableNode... nodes) {
                return otherwise(new DefaultBranchNode(nodes));
            }

            public IfConditionNode end() {
                return new IfConditionNode(rule, thenBranch);
            }

        }

    }

    public static class WhileWhen {

        protected Rule rule;

        WhileWhen(Rule rule) {
            this.rule = rule;
        }

        public WhileConditionNode then(BranchNode branch) {
            return new WhileConditionNode(rule, branch);
        }

        public WhileConditionNode then(ExecutableNode... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

    }

    public static class DoWhileWhen extends WhileWhen {

        DoWhileWhen(Rule rule) {
            super(rule);
        }

        @Override
        public WhileConditionNode then(BranchNode branch) {
            return new DoWhileConditionNode(rule, branch);
        }

    }

    public static class TryCatchFinally {

        private BranchNode tryBranch;

        private Map<Class<? extends Exception>, BranchNode> catchMap;

        TryCatchFinally(BranchNode tryBranch) {
            this.tryBranch = tryBranch;
            this.catchMap = new HashMap<>();
        }

        public TryCatchFinally catchOn(Class<? extends Exception> expClass, BranchNode branch) {
            if (expClass == null) {
                throw new NullPointerException();
            }
            this.catchMap.put(expClass, branch);
            return this;
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, ExecutableNode... nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode doFinally(BranchNode branch) {
            return new TryCatchFinallyNode(tryBranch, catchMap, branch);
        }

        public TryCatchFinallyNode doFinally(ExecutableNode... nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }
    }
}
