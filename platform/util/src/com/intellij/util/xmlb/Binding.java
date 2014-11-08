/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util.xmlb;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

abstract class Binding {
  protected final Accessor myAccessor;

  protected Binding(Accessor accessor) {
    myAccessor = accessor;
  }

  @NotNull
  public Accessor getAccessor() {
    return myAccessor;
  }

  @Nullable
  public abstract Object serialize(Object o, @Nullable Object context, SerializationFilter filter);

  @Nullable
  public abstract Object deserialize(Object context, @NotNull Object node);

  public abstract boolean isBoundTo(Object node);

  public abstract Class getBoundNodeType();

  public void init() {
  }

  @SuppressWarnings("CastToIncompatibleInterface")
  @Nullable
  public static Object deserializeList(@NotNull Binding binding, Object context, @NotNull List<?> nodes) {
    if (binding instanceof MultiNodeBinding) {
      return ((MultiNodeBinding)binding).deserializeList(context, nodes);
    }
    else {
      assert nodes.size() == 1;
      return binding.deserialize(context, nodes.get(0));
    }
  }
}
