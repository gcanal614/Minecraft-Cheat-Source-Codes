package client.metaware.impl.utils.pathfinder;

import client.metaware.impl.utils.render.StringUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class GotoAI
{
    private PathFinder pathFinder;
    private PathProcessor processor;
    private boolean done;
    private boolean failed;
    private EntityLivingBase target;

    public GotoAI(final BlockPos goal) {
        this.pathFinder = new PathFinder(goal);
    }

    public GotoAI(final EntityLivingBase entity) {
        this.pathFinder = new PathFinder(new BlockPos((int)entity.posX, StringUtils.removeDecimals(entity.posY), (int)entity.posZ));
        this.target = entity;
    }

    public void update() {
        if (!this.pathFinder.isDone()) {
            if (this.processor != null) {
                this.processor.lockControls();
            }
            this.pathFinder.think();
            if (!this.pathFinder.isDone()) {
                if (this.pathFinder.isFailed()) {
                    this.failed = true;
                }
                return;
            }
            this.pathFinder.formatPath();
            this.processor = this.pathFinder.getProcessor();
        }
        if (this.processor != null && !this.pathFinder.isPathStillValid(this.processor.getIndex())) {
            this.pathFinder = new PathFinder(this.pathFinder.getGoal());
            return;
        }
        this.processor.process();
        if (this.processor.isFailed()) {
            this.failed = true;
        }
        if (this.processor.isDone()) {
            this.done = true;
        }
    }

    public void update(final String processor) {
        if (!this.pathFinder.isDone()) {
            if (this.processor != null) {
                this.processor.lockControls();
            }
            this.pathFinder.think();
            if (!this.pathFinder.isDone()) {
                if (this.pathFinder.isFailed()) {
                    this.failed = true;
                }
                return;
            }
            this.pathFinder.formatPath();
            if (processor.equalsIgnoreCase("infiniteaura")) {
                this.processor = this.pathFinder.getInfiniteAuraProcessor(this.target);
            }
        }
        if (this.processor != null && !this.pathFinder.isPathStillValid(this.processor.getIndex())) {
            this.pathFinder = new PathFinder(this.pathFinder.getGoal());
            return;
        }
        this.processor.process();
        if (this.processor.isFailed()) {
            this.failed = true;
        }
        if (this.processor.isDone()) {
            this.done = true;
        }
    }

    public void stop() {
        if (this.processor != null) {
            this.processor.stop();
        }
    }

    public final boolean isDone() {
        return this.done;
    }

    public final boolean isFailed() {
        return this.failed;
    }
}