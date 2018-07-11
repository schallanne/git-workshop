package playground.view

import scala.xml.Node

import mhtml._
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.raw.HTMLInputElement
import scalacss.DevDefaults._

import playground.model.Todo
import playground.util.Colors

object TodoList {
  def apply(): Node = {
    val todos: Var[Seq[Todo]] = Var(
      Seq(
        Todo("learn usefull git commands", isCompleted = false),
        Todo("play with Scala.js", isCompleted = false)
      )
    )

    lazy val result =
      <div class={ Style.root.htmlClass }>
        <p>You can add or remove todo items.</p>

        { addTodo }

        <div class={ Style.presentation.htmlClass }>
          { presentation }
        </div>

        <ul class={ Style.todos.htmlClass }>
          { todos.map(_.zipWithIndex.map {
            case (todo, index) => todoItem(todo, index)
          } ) }
        </ul>
      </div>

    lazy val addTodo =
      <input
        class={ Style.addTodo.htmlClass }
        autofocus="true"
        placeholder="Add something to do"
        onkeydown={ event: KeyboardEvent =>
          (event.currentTarget, event.keyCode) match {
            case (input: HTMLInputElement, KeyCode.Enter) =>
              input.value.trim match {
                case "" =>
                case todo =>
                  todos.update(Todo(todo, isCompleted = false) +: _)
                  input.value = ""
              }
            case _ =>
          }
        }
      />

    lazy val presentation =
      todos.map {
        case xs =>
          val totalCount = xs.length
          val uncompletedTodos = xs.filter(_.isCompleted == false)
          val uncompletedCount = uncompletedTodos.length

          val among =
            if (totalCount == 0 || uncompletedCount == totalCount)
              ""
            else
              s" among ${totalCount} thing${if (totalCount > 1) "s" else ""}"

          uncompletedTodos match {
            case Nil =>
              s"There is nothing to do$among."
            case Seq(_) =>
              s"There is 1 thing to do$among:"
            case xs =>
              s"There are ${xs.length} things to do$among:"
          }
      }

    def todoItem(todo: Todo, index: Int) =
      <li
        class={ StyleUtil.classes(
          Style.todo.htmlClass -> true,
          Style.completedTodo.htmlClass -> todo.isCompleted
        ) }
      >
        <span
          onclick={ () => todos.update { xs =>
            xs.updated(index, todo.copy(isCompleted = !todo.isCompleted))
          } }
        >
          { todo.title }
        </span>

        <button
          class={ Style.removeTodo.htmlClass }
          onclick={ () => todos.update(_.patch(index, Nil, 1)) }
        >
          ✗
        </button>
      </li>

    result
  }

  object Style extends StyleSheet.Inline {
    import dsl._

    val root = style(
      margin(3.em)
    )

    val addTodo = style(
      marginBottom(1.em)
    )

    val presentation = style(
      marginBottom(2.em)
    )

    val todos = style(
      marginLeft(2.em),
      listStyleType := "disc"
    )

    val todo = style(
      marginBottom(1.em),
      cursor.pointer
    )

    val completedTodo = style(
      textDecoration := "line-through"
    )

    val removeTodo = style(
      cursor.pointer,
      color(Colors.color2),
      backgroundColor(transparent),
      border.none,
      fontSize(1.2.em)
    )
  }
}
