/*
  Copyright 2018 MyMiller Consulting LLC.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License.  You may obtain a copy
  of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
  License for the specific language governing permissions and limitations under
  the License.
 */
/*

 */
package name.mymiller.httpserver.handlers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author jmiller
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
/*
  Annotation indicating a class is a ContextHandlerInterface and should be
  loaded with the given context.

  @author jmiller

 */
public @interface HttpHandlerAnnotation {
    /**
     *
     * @return Context of this Handler
     */
    String context() default "/";
}
