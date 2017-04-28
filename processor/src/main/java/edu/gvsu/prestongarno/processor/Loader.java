package edu.gvsu.prestongarno.processor;

import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;

/*****************************************
 * Created by preston on 4/26/17.
 *
 * Plugin for the ServiceLoader
 ****************************************/
public class Loader implements Plugin {

	@Override
	public String getName() {
		return "Try-With-Resources<<";
	}

	/*****************************************
	 * @param javacTask the comp. task
	 * @param strings args
	 ****************************************/
	@Override
	public void init(JavacTask javacTask, String... strings) {
		javacTask.addTaskListener(createTaskListener(javacTask));
	}

	/*****************************************
	 * @param task the compilation task
	 * @return TaskListener that transforms try-with-res to
	 * a safe but backwards compatible version
	 ****************************************/
	public static TaskListener createTaskListener(JavacTask task) {
		return new TaskListenerImpl(task);
	}


	/*****************************************
	 * The tasklistener waits until the
	 * processing rounds are over to scan the AST
	 ****************************************/
	static final class TaskListenerImpl implements TaskListener {

		private BasicJavacTask task;

		public TaskListenerImpl(JavacTask javacTask) { task = (BasicJavacTask) javacTask; }

		public void started(TaskEvent taskEvent) {
			if(taskEvent.getKind() == TaskEvent.Kind.ANALYZE) {
				TryTreeTranslator ttt = new TryTreeTranslator(task.getContext());
				taskEvent.getCompilationUnit().getTypeDecls().forEach(o -> ttt.translate((JCTree) o));
			}
		}
		public void finished(TaskEvent e) {}
	}
}
