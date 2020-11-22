/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @author jmiller Abstract class to build Jobs for the Job Manager
 */
public abstract class Action extends RecursiveAction implements Runnable {
    /**
     * List of jobs to be completed as a portion of this
     * name.mymiller.action.
     */
    protected List<Action> subJobs = null;

    /**
     * Constructor allowing direct subclassing
     */
    protected Action() {
        this.subJobs = new ArrayList<>();
    }

    /**
     * Method to add a Sub Job to this name.mymiller.action
     *
     * @param action Job to add
     */
    protected void addSubJob(final Action action) {
        this.subJobs.add(action);
    }

    /**
     * Override this method to implement post-processing, after all sub-jobs have
     * completed.
     */
    protected void postProcess() {

    }

    /**
     * Overrite this method to implement any pre-run processing, before the Run
     * method is called
     */
    protected void preProcess() {

    }

    /**
     * Method to implement to perform the actions necessary for the Job. Only called if split() returns false;
     */
    protected abstract void process();

    /**
     * Method to remove a name.mymiller.action from the list
     *
     * @param action Job to remove
     */
    protected void removeSubJob(final Action action) {
        this.subJobs.remove(action);
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        this.preProcess();
        this.process();
        for (final Action action : this.subJobs) {
            TaskManager.getInstance().invoke(action);
        }
        this.postProcess();
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        this.run();
    }
}
