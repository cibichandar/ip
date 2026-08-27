# UI Test Plan

Add one section per command-line UI scenario. The `test-ui` skill runs these cases in order and stops at the first mismatch.

Copy the following template for each real test case (remove the indentation):

```markdown
    ## Test case: <short name>

    **Aim:** <what behavior is verified>
    **Command:** `<command to run>`
    **Inputs:**
    ```text
    <stdin, exactly as supplied, or leave empty for no input>
    ```
    **Expected output:**
    ```text
    <complete combined stdout and stderr, exactly as it should appear>
    ```
```

## Test case: create and list all task types

**Aim:** Verify that todos, deadlines, and events are parsed and displayed with their type and date/time text.
**Command:** `mkdir -p /tmp/fein-ui-build && javac -d /tmp/fein-ui-build src/main/java/*.java && java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```
**Expected output:**
```text
____________________________________________________________________________________________________
oooooooooooo           o8o                     
`888'     `8           `"'                     
 888          .ooooo.  oooo  ooo. .oo.         
 888oooo8    d88' `88b `888  `888P"Y88b        
 888    "    888ooo888  888   888   888        
 888         888    .o  888   888   888        
o888o        `Y8bod8P' o888o o888o o888o       

Hello! I'm Fein.
What can I do for you?
____________________________________________________________________________________________________
____________________________________________________________________________________________________
 Got it. I've added this task:
   [T][ ] borrow book
 Now you have 1 tasks in the list.
____________________________________________________________________________________________________
____________________________________________________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Sunday)
 Now you have 2 tasks in the list.
____________________________________________________________________________________________________
____________________________________________________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________________________________________________
____________________________________________________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: Sunday)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________________________________________________
____________________________________________________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________________________________________________

```
