package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.branch.DefaultBranch;
import cn.ideabuffer.process.condition.DoWhileConditionNode;
import cn.ideabuffer.process.condition.IfConditionNode;
import cn.ideabuffer.process.condition.WhileConditionNode;
import cn.ideabuffer.process.rule.Rule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Nodes {

    public static IfWhen newIf(Rule rule) {
        return new IfWhen(rule);
    }

    public static WhileWhen newWhile(Rule rule) {
        return new WhileWhen(rule);
    }

    public static DoWhileWhen newDoWhile(Rule rule) {
        return new DoWhileWhen(rule);
    }

    public static TryCatchFinally newTry(Branch branch) {
        return new TryCatchFinally(branch);
    }

    public static TryCatchFinally newTry(ExecutableNode... nodes) {
        return new TryCatchFinally(new DefaultBranch(nodes));
    }

    public static class IfWhen {

        private Rule rule;

        public IfWhen(Rule rule) {
            this.rule = rule;
        }

        public IfWhenBuilder then(Branch branch) {
            return new IfWhenBuilder(rule, branch);
        }

        public IfWhenBuilder then(ExecutableNode... nodes) {
            return then(new DefaultBranch(nodes));
        }

        public class IfWhenBuilder {

            private Rule rule;

            private Branch thenBranch;

            public IfWhenBuilder(Rule rule, Branch thenBranch) {
                this.rule = rule;
                this.thenBranch = thenBranch;
            }

            public IfConditionNode otherwise(Branch branch) {
                return new IfConditionNode(rule, thenBranch, branch);
            }

            public IfConditionNode otherwise(ExecutableNode... nodes) {
                return otherwise(new DefaultBranch(nodes));
            }

            public IfConditionNode end() {
                return new IfConditionNode(rule, thenBranch);
            }

        }

    }

    public static class WhileWhen {

        protected Rule rule;

        public WhileWhen(Rule rule) {
            this.rule = rule;
        }

        public WhileConditionNode then(Branch branch) {
            return new WhileConditionNode(rule, branch);
        }

        public WhileConditionNode then(ExecutableNode... nodes) {
            return then(new DefaultBranch(nodes));
        }

    }

    public static class DoWhileWhen extends WhileWhen {

        public DoWhileWhen(Rule rule) {
            super(rule);
        }

        @Override
        public WhileConditionNode then(Branch branch) {
            return new DoWhileConditionNode(rule, branch);
        }

    }

    public static class TryCatchFinally {

        private Branch tryBranch;

        private Map<Class<? extends Throwable>, Branch> catchMap;

        public TryCatchFinally(Branch tryBranch) {
            this.tryBranch = tryBranch;
            this.catchMap = new HashMap<>();
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, Branch branch) {
            if (expClass == null) {
                throw new NullPointerException();
            }
            this.catchMap.put(expClass, branch);
            return this;
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, ExecutableNode... nodes) {
            return catchOn(expClass, new DefaultBranch(nodes));
        }

        public TryCatchFinallyNode doFinally(Branch branch) {
            return new TryCatchFinallyNode(tryBranch, catchMap, branch);
        }

        public TryCatchFinallyNode doFinally(ExecutableNode... nodes) {
            return doFinally(new DefaultBranch(nodes));
        }
    }
}
