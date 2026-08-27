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

## Test case: unknown command

**Aim:** Verify that Fein rejects commands it does not recognise.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
blah
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
 OOPS!!! Fein don't know that one, try again
____________________________________________________________________________________________________

```

## Test case: todo without description

**Aim:** Verify that a todo requires a description.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
todo
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
 OOPS!!! Can't fein for nothing, give the todo a description
____________________________________________________________________________________________________

```

## Test case: deadline without description

**Aim:** Verify that a deadline requires a description.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
deadline
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
 OOPS!!! Empty deadline? Fein needs a description too
____________________________________________________________________________________________________

```

## Test case: deadline without due date marker

**Aim:** Verify that a deadline requires a `/by` marker.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
deadline return book
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
 OOPS!!! When's it due? Add a /by
____________________________________________________________________________________________________

```

## Test case: deadline with blank due date

**Aim:** Verify that a deadline cannot have an empty due date.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
deadline return book /by
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
 OOPS!!! You left the date blank after /by
____________________________________________________________________________________________________

```

## Test case: event without description

**Aim:** Verify that an event requires a description.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
event
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
 OOPS!!! Empty event? Fein needs a description too
____________________________________________________________________________________________________

```

## Test case: event without from and to

**Aim:** Verify that an event requires both `/from` and `/to` markers.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
event meeting
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
 OOPS!!! Fein needs a /from and /to for this one
____________________________________________________________________________________________________

```

## Test case: event without to

**Aim:** Verify that an event requires an ending time.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
event meeting /from Mon 2pm
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
 OOPS!!! Missing the /to, when does it end?
____________________________________________________________________________________________________

```

## Test case: event with blank from

**Aim:** Verify that an event cannot have an empty starting time.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
event meeting /from /to 4pm
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
 OOPS!!! You left /from blank
____________________________________________________________________________________________________

```

## Test case: mark without task number

**Aim:** Verify that marking requires a task number.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
mark
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
 OOPS!!! Mark what? Give Fein a task number
____________________________________________________________________________________________________

```

## Test case: mark with non-number

**Aim:** Verify that marking rejects non-numeric task identifiers.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
mark abc
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
 OOPS!!! That's not a number, Fein can't read minds
____________________________________________________________________________________________________

```

## Test case: mark nonexistent task

**Aim:** Verify that marking rejects a task number that is not in the list.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
mark 999
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
 OOPS!!! Task 999 don't exist, check your list again
____________________________________________________________________________________________________

```

## Test case: mark task zero

**Aim:** Verify that task numbering starts at one.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
mark 0
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
 OOPS!!! Task numbers start from 1, not 0
____________________________________________________________________________________________________

```

## Test case: list on empty list

**Aim:** Verify the response when the task list is empty.
**Command:** `java -cp /tmp/fein-ui-build Fein`
**Inputs:**
```text
list
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
 Nothing on the list yet, Fein's waiting on you
____________________________________________________________________________________________________

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
