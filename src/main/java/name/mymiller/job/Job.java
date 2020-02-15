/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.job;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jmiller Abstract class to build Jobs for the Job Manager
 */
public abstract class Job implements Runnable {
    /**
     * List of jobs to be completed as a portion of this
     * name.mymiller.job.
     */
    protected List<Job> subJobs = null;

    /**
     * Constructor allowing direct subclassing
     */
    protected Job() {
        this.subJobs = new ArrayList<>();
    }

    /**
     * Method to add a Sub Job to this name.mymiller.job
     *
     * @param job Job to add
     */
    protected void addSubJob(final Job job) {
        this.subJobs.add(job);
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
     * Method to implement to perform the actions necessary for the Job.
     */
    protected abstract void process();

    /**
     * Method to remove a name.mymiller.job from the list
     *
     * @param job Job to remove
     */
    protected void removeSubJob(final Job job) {
        this.subJobs.remove(job);
    }

    @Override
    public void run() {

        this.preProcess();
        this.process();
        for (final Job job : this.subJobs) {
            JobManager.getInstance().submit(job);
        }
        this.postProcess();
    }
}
