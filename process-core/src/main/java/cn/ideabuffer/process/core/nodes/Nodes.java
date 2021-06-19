package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.aggregate.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.impl.*;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.proxy.DoWhileProcessorProxy;
import cn.ideabuffer.process.core.processors.wrapper.proxy.IfProcessorProxy;
import cn.ideabuffer.process.core.processors.wrapper.proxy.TryCatchFinallyProcessorProxy;
import cn.ideabuffer.process.core.processors.wrapper.proxy.WhileProcessorProxy;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Nodes {

    private Nodes() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> ProcessNode<R> newProcessNode() {
        return new ProcessNode<>();
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor) {
        return newProcessNode(processor, null, null);
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return newProcessNode(processor, null, readableKeys, writableKeys);
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor, KeyMapper mapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return new ProcessNode<>(processor, mapper, readableKeys, writableKeys);
    }

    public static NodeGroup newGroup() {
        return new NodeGroup();
    }

    public static BranchNode newBranch() {
        return new DefaultBranchNode();
    }

    public static BranchNode newBranch(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull List<ExecutableNode<?, ?>> nodes) {
        return new DefaultBranchNode(rule, nodes);
    }

    public static BranchNode newBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(null, Arrays.asList(nodes));
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(rule, Arrays.asList(nodes));
    }

    public static ParallelBranchNode newParallelBranchNode() {
        return newParallelBranchNode(null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule) {
        return newParallelBranchNode(rule, null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, List<BranchNode> branches) {
        return newParallelBranchNode(rule, null, branches);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, Executor executor, List<BranchNode> branches) {
        return new DefaultParallelBranchNode(rule, executor, branches);
    }

    public static <R> UnitAggregatableNode<R> newUnitAggregatableNode() {
        return new DefaultUnitAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode() {
        return new DefaultGenericAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode(
        GenericAggregateProcessorImpl<P, R> processor) {
        return new DefaultGenericAggregatableNode<>(processor);
    }

    public static <R> DistributeAggregatableNode<R> newDistributeAggregatableNode() {
        return new DefaultDistributeAggregatableNode<>();
    }

    public static IfWhen newIf(Rule rule, Key<?>... readableKeys) {
        return newIf(rule, null, readableKeys);
    }

    public static IfWhen newIf(Rule rule, KeyMapper keyMapper, Key<?>... readableKeys) {
        return new IfWhen(rule, keyMapper, readableKeys);
    }

    public static WhileWhen newWhile(Rule rule, Key<?>... readableKeys) {
        return newWhile(rule, null, readableKeys);
    }

    public static WhileWhen newWhile(Rule rule, KeyMapper keyMapper, Key<?>... readableKeys) {
        return new WhileWhen(rule, keyMapper, readableKeys);
    }

    public static WhileWhen newWhile(Rule rule, Set<Key<?>> readableKeys) {
        return newWhile(rule, null, readableKeys, null);
    }

    public static WhileWhen newWhile(Rule rule, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return newWhile(rule, null, readableKeys, writableKeys);
    }

    public static WhileWhen newWhile(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return new WhileWhen(rule, keyMapper, readableKeys, writableKeys);
    }

    public static DoWhileWhen newDoWhile(Rule rule, Key<?>... readableKeys) {
        return new DoWhileWhen(rule, readableKeys);
    }

    public static WhileWhen newDoWhile(Rule rule, KeyMapper keyMapper, Key<?>... readableKeys) {
        return new DoWhileWhen(rule, keyMapper, readableKeys);
    }

    public static WhileWhen newDoWhile(Rule rule, Set<Key<?>> readableKeys) {
        return newDoWhile(rule, null, readableKeys, null);
    }

    public static WhileWhen newDoWhile(Rule rule, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return newDoWhile(rule, null, readableKeys, writableKeys);
    }

    public static WhileWhen newDoWhile(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        return new DoWhileWhen(rule, keyMapper, readableKeys, writableKeys);
    }

    public static TryCatchFinally newTry(BranchNode branch) {
        return new TryCatchFinally(branch);
    }

    public static TryCatchFinally newTry(ExecutableNode<?, ?>... nodes) {
        return new TryCatchFinally(new DefaultBranchNode(nodes));
    }

    public static TryCatchFinally newTry(List<ExecutableNode<?, ?>> nodes) {
        return new TryCatchFinally(new DefaultBranchNode(nodes));
    }

    public static class IfWhen {

        private Rule rule;
        private KeyMapper keyMapper;
        private Set<Key<?>> readableKeys;
        private Set<Key<?>> writableKeys;
        private List<StatusWrapperHandler> handlers;

        IfWhen(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys) {
            this(rule, keyMapper, readableKeys, null);
        }

        IfWhen(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
            this.rule = rule;
            this.keyMapper = keyMapper;
            this.readableKeys = readableKeys;
            this.writableKeys = writableKeys;
        }

        IfWhen(Rule rule,KeyMapper keyMapper, Key<?>... readableKeys) {
            this.rule = rule;
            this.keyMapper = keyMapper;
            if (readableKeys != null) {
                this.readableKeys = Arrays.stream(readableKeys).collect(Collectors.toSet());
            }
        }

        public IfWhen wrap(@NotNull StatusWrapperHandler handler) {
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.add(handler);
            return this;
        }

        public IfWhen wrap(@NotNull List<StatusWrapperHandler> handlers) {
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.addAll(handlers);
            return this;
        }

        public IfWhenBuilder then(@NotNull BranchNode branch) {
            return new IfWhenBuilder(rule, branch, keyMapper, readableKeys, writableKeys, handlers);
        }

        public IfWhenBuilder then(@NotNull ExecutableNode<?, ?>... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public IfWhenBuilder then(@NotNull List<ExecutableNode<?, ?>> nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public class IfWhenBuilder {

            private Rule rule;
            private BranchNode thenBranch;
            private KeyMapper keyMapper;
            private Set<Key<?>> readableKeys;
            private Set<Key<?>> writableKeys;
            private List<StatusWrapperHandler> handlers;

            IfWhenBuilder(Rule rule, BranchNode thenBranch, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys, List<StatusWrapperHandler> handlers) {
                this.rule = rule;
                this.thenBranch = thenBranch;
                this.keyMapper = keyMapper;
                this.readableKeys = readableKeys;
                this.writableKeys = writableKeys;
                this.handlers = handlers;
            }

            public IfConditionNode otherwise(BranchNode falseBranch) {
                IfProcessor wrapped = new IfProcessorImpl(rule, thenBranch, falseBranch, keyMapper, readableKeys, writableKeys);
                wrapped = IfProcessorProxy.wrap(wrapped, handlers);
                return new IfConditionNode(wrapped);
            }

            public IfConditionNode otherwise(ExecutableNode<?, ?>... nodes) {
                return otherwise(new DefaultBranchNode(nodes));
            }

            public IfConditionNode end() {
                IfProcessor wrapped = new IfProcessorImpl(rule, thenBranch, null, keyMapper, readableKeys, writableKeys);
                wrapped = IfProcessorProxy.wrap(wrapped, handlers);
                return new IfConditionNode(wrapped);
            }

        }

    }

    public static class WhileWhen {

        protected Rule rule;
        protected KeyMapper keyMapper;
        protected Set<Key<?>> readableKeys;
        protected Set<Key<?>> writableKeys;
        protected List<StatusWrapperHandler> handlers;

        WhileWhen(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys) {
            this(rule, keyMapper, readableKeys, null);
        }

        WhileWhen(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
            this.rule = rule;
            this.keyMapper = keyMapper;
            this.readableKeys = readableKeys;
            this.writableKeys = writableKeys;
        }

        WhileWhen(Rule rule, KeyMapper keyMapper, Key<?>... readableKeys) {
            this.rule = rule;
            this.keyMapper = keyMapper;
            if (readableKeys != null) {
                this.readableKeys = Arrays.stream(readableKeys).collect(Collectors.toSet());
            }
        }

        WhileWhen(Rule rule, Key<?>... readableKeys) {
            this(rule, null, readableKeys);
        }

        public WhileWhen wrap(@NotNull StatusWrapperHandler handler) {
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.add(handler);
            return this;
        }

        public WhileWhen wrap(@NotNull List<StatusWrapperHandler> handlers) {
            if (handlers.isEmpty()) {
                return this;
            }
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.addAll(handlers);
            return this;
        }

        public WhileConditionNode then(BranchNode branch) {
            WhileProcessor wrapped = new WhileProcessorImpl(rule, branch, keyMapper, readableKeys, writableKeys);
            wrapped = WhileProcessorProxy.wrap(wrapped, this.handlers);
            return new WhileConditionNode(wrapped);
        }

        public WhileConditionNode then(ExecutableNode<?, ?>... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public WhileConditionNode then(List<ExecutableNode<?, ?>> nodes) {
            return then(new DefaultBranchNode(null, nodes));
        }

    }

    public static class DoWhileWhen extends WhileWhen {

        DoWhileWhen(Rule rule, KeyMapper keyMapper, Key<?>... readableKeys) {
            super(rule, keyMapper, readableKeys);
        }

        DoWhileWhen(Rule rule, KeyMapper keyMapper, Set<Key<?>> readableKeys) {
            super(rule, keyMapper, readableKeys);
        }

        DoWhileWhen(Rule rule, Key<?>... readableKeys) {
            super(rule, readableKeys);
        }

        DoWhileWhen(Rule rule, KeyMapper keyMapper,
            Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
            super(rule, keyMapper, readableKeys, writableKeys);
        }
        @Override
        public DoWhileWhen wrap(@NotNull StatusWrapperHandler handler) {
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.add(handler);
            return this;
        }

        @Override
        public DoWhileWhen wrap(@NotNull List<StatusWrapperHandler> handlers) {
            if (handlers.isEmpty()) {
                return this;
            }
            if (this.handlers == null) {
                this.handlers = new ArrayList<>();
            }
            this.handlers.addAll(handlers);
            return this;
        }
        @Override
        public DoWhileConditionNode then(BranchNode branch) {
            DoWhileProcessor wrapped = new DoWhileProcessorImpl(rule, branch, keyMapper, readableKeys, writableKeys);
            wrapped = DoWhileProcessorProxy.wrap(wrapped, this.handlers);
            return new DoWhileConditionNode(wrapped);
        }

        @Override
        public DoWhileConditionNode then(ExecutableNode<?, ?>... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        @Override
        public DoWhileConditionNode then(List<ExecutableNode<?, ?>> nodes) {
            return then(new DefaultBranchNode(null, nodes));
        }

    }

    public static class TryCatchFinally {

        private BranchNode tryBranch;

        private List<TryCatchFinallyNode.CatchMapper> catchMapperList;

        private List<StatusWrapperHandler> handlers;

        TryCatchFinally(BranchNode tryBranch) {
            this.tryBranch = tryBranch;
            this.catchMapperList = new LinkedList<>();
        }

        public TryCatchFinally wrap(@NotNull StatusWrapperHandler handler) {
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.add(handler);
            return this;
        }

        public TryCatchFinally wrap(@NotNull List<StatusWrapperHandler> handlers) {
            if (handlers.isEmpty()) {
                return this;
            }
            if (this.handlers == null) {
                this.handlers = new LinkedList<>();
            }
            this.handlers.addAll(handlers);
            return this;
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, BranchNode branch) {
            if (expClass == null) {
                throw new NullPointerException();
            }
            this.catchMapperList.add(new TryCatchFinallyNode.CatchMapper(expClass, branch));
            return this;
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, ExecutableNode<?, ?>... nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, List<ExecutableNode<?, ?>> nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, Processor<?>... processors) {
            if (processors == null || processors.length == 0) {
                return catchOn(expClass, new DefaultBranchNode());
            }
            List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
                Collectors.toList());
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode doFinally(BranchNode finallyBranch) {
            TryCatchFinallyProcessor wrapped = new TryCatchFinallyProcessorImpl(tryBranch, catchMapperList, finallyBranch);
            wrapped = TryCatchFinallyProcessorProxy.wrap(wrapped, this.handlers);
            return new TryCatchFinallyNode(wrapped);
        }

        public TryCatchFinallyNode doFinally(ExecutableNode<?, ?>... nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode doFinally(Processor<?>... processors) {
            BranchNode branch = null;
            if (processors == null || processors.length == 0) {
                return doFinally(branch);
            }
            branch = new DefaultBranchNode(processors);
            return doFinally(branch);
        }

        public TryCatchFinallyNode doFinally(List<ExecutableNode<?, ?>> nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }
    }
}
