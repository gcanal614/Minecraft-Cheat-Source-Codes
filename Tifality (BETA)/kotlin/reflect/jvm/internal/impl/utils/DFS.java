/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class DFS {
    public static <N, R> R dfs(@NotNull Collection<N> nodes, @NotNull Neighbors<N> neighbors, @NotNull Visited<N> visited, @NotNull NodeHandler<N, R> handler) {
        if (nodes == null) {
            DFS.$$$reportNull$$$0(0);
        }
        if (neighbors == null) {
            DFS.$$$reportNull$$$0(1);
        }
        if (visited == null) {
            DFS.$$$reportNull$$$0(2);
        }
        if (handler == null) {
            DFS.$$$reportNull$$$0(3);
        }
        for (N node : nodes) {
            DFS.doDfs(node, neighbors, visited, handler);
        }
        return handler.result();
    }

    public static <N, R> R dfs(@NotNull Collection<N> nodes, @NotNull Neighbors<N> neighbors, @NotNull NodeHandler<N, R> handler) {
        if (nodes == null) {
            DFS.$$$reportNull$$$0(4);
        }
        if (neighbors == null) {
            DFS.$$$reportNull$$$0(5);
        }
        if (handler == null) {
            DFS.$$$reportNull$$$0(6);
        }
        return DFS.dfs(nodes, neighbors, new VisitedWithSet(), handler);
    }

    public static <N> Boolean ifAny(@NotNull Collection<N> nodes, @NotNull Neighbors<N> neighbors, final @NotNull Function1<N, Boolean> predicate) {
        if (nodes == null) {
            DFS.$$$reportNull$$$0(7);
        }
        if (neighbors == null) {
            DFS.$$$reportNull$$$0(8);
        }
        if (predicate == null) {
            DFS.$$$reportNull$$$0(9);
        }
        final boolean[] result2 = new boolean[1];
        return (Boolean)DFS.dfs(nodes, neighbors, new AbstractNodeHandler<N, Boolean>(){

            @Override
            public boolean beforeChildren(N current) {
                if (((Boolean)predicate.invoke(current)).booleanValue()) {
                    result2[0] = true;
                }
                return !result2[0];
            }

            @Override
            public Boolean result() {
                return result2[0];
            }
        });
    }

    public static <N> void doDfs(@NotNull N current, @NotNull Neighbors<N> neighbors, @NotNull Visited<N> visited, @NotNull NodeHandler<N, ?> handler) {
        if (current == null) {
            DFS.$$$reportNull$$$0(22);
        }
        if (neighbors == null) {
            DFS.$$$reportNull$$$0(23);
        }
        if (visited == null) {
            DFS.$$$reportNull$$$0(24);
        }
        if (handler == null) {
            DFS.$$$reportNull$$$0(25);
        }
        if (!visited.checkAndMarkVisited(current)) {
            return;
        }
        if (!handler.beforeChildren(current)) {
            return;
        }
        for (N neighbor : neighbors.getNeighbors(current)) {
            DFS.doDfs(neighbor, neighbors, visited, handler);
        }
        handler.afterChildren(current);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "nodes";
                break;
            }
            case 1: 
            case 5: 
            case 8: 
            case 11: 
            case 15: 
            case 18: 
            case 21: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "neighbors";
                break;
            }
            case 2: 
            case 12: 
            case 16: 
            case 19: 
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visited";
                break;
            }
            case 3: 
            case 6: 
            case 13: 
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "handler";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "predicate";
                break;
            }
            case 10: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "node";
                break;
            }
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "current";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/utils/DFS";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "dfs";
                break;
            }
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[2] = "ifAny";
                break;
            }
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                objectArray = objectArray2;
                objectArray2[2] = "dfsFromNode";
                break;
            }
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                objectArray = objectArray2;
                objectArray2[2] = "topologicalOrder";
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                objectArray = objectArray2;
                objectArray2[2] = "doDfs";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }

    public static abstract class NodeHandlerWithListResult<N, R>
    extends CollectingNodeHandler<N, R, LinkedList<R>> {
        protected NodeHandlerWithListResult() {
            super(new LinkedList());
        }
    }

    public static abstract class CollectingNodeHandler<N, R, C extends Iterable<R>>
    extends AbstractNodeHandler<N, C> {
        @NotNull
        protected final C result;

        protected CollectingNodeHandler(@NotNull C result2) {
            if (result2 == null) {
                CollectingNodeHandler.$$$reportNull$$$0(0);
            }
            this.result = result2;
        }

        @Override
        @NotNull
        public C result() {
            C c = this.result;
            if (c == null) {
                CollectingNodeHandler.$$$reportNull$$$0(1);
            }
            return c;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            RuntimeException runtimeException;
            Object[] objectArray;
            Object[] objectArray2;
            int n2;
            String string;
            switch (n) {
                default: {
                    string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                }
                case 1: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 1: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "result";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/utils/DFS$CollectingNodeHandler";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/utils/DFS$CollectingNodeHandler";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "result";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 1: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 1: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    public static class VisitedWithSet<N>
    implements Visited<N> {
        private final Set<N> visited;

        public VisitedWithSet() {
            this(new HashSet());
        }

        public VisitedWithSet(@NotNull Set<N> visited) {
            if (visited == null) {
                VisitedWithSet.$$$reportNull$$$0(0);
            }
            this.visited = visited;
        }

        @Override
        public boolean checkAndMarkVisited(N current) {
            return this.visited.add(current);
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visited", "kotlin/reflect/jvm/internal/impl/utils/DFS$VisitedWithSet", "<init>"));
        }
    }

    public static abstract class AbstractNodeHandler<N, R>
    implements NodeHandler<N, R> {
        @Override
        public boolean beforeChildren(N current) {
            return true;
        }

        @Override
        public void afterChildren(N current) {
        }
    }

    public static interface Visited<N> {
        public boolean checkAndMarkVisited(N var1);
    }

    public static interface Neighbors<N> {
        @NotNull
        public Iterable<? extends N> getNeighbors(N var1);
    }

    public static interface NodeHandler<N, R> {
        public boolean beforeChildren(N var1);

        public void afterChildren(N var1);

        public R result();
    }
}

