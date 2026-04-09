---
layout: page
title: User Guide
---

HomeChef-Helper (HomeChef) is a simple, intuitive desktop app for managing orders and payments for any **self-made food
business owners!**<br>
From new cooks with no experience managing their orders, to expert home food business owners with extensive knowledge,
the app helps to **consolidate the order and food information in an easy to read format**, helping you get things done
faster!<br>
With a simple typing interface and a clear order list and food menu, this app is here to help you **manage orders quick
** if you can **type fast**.

- Table of Contents
  {:toc}

---

# Looking to get started?<br> Here's a quick guide:

1.  Ensure you have Java `17` or above installed in your Computer.<br>
    A tutorial on how to download Java `17` can be
    found [here](https://se-education.org/guides/tutorials/javaInstallation.html).<br>
    **Mac users:** Ensure you have the precise JDK version
    prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1.  Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T13-4/tp/releases).<br>Only the
    `.jar` file is needed, not the source code.

1.  Copy the file to the folder you want to use as the _home folder_ for your HomeChef.

1.  Double-click on the `homechef.jar` file to launch the app.<br>
    If that does not work, try the following:

    > 1. Open a command terminal. <br>(Command Prompt or Powershell on Windows, Terminal on Mac)<br>
    > 1. Use the `cd` command to navigate into the folder you put the jar file in.<br> For example:<br>

         `cd Desktop/Folder1/FolderContainingHomeChef`<br>

    > 1. Type the `java -jar homechef.jar` command to run the application.<br>

    If successful, a screen similar to the one below should appear in a few seconds. The app contains some sample data
    for you to get an idea of how it functions.<br>

    ![Ui](images/Ui.png)

1.  Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
    open the help window.<br>
    Some example commands you can try:
    - `list` : Lists all orders. Good for resetting the display to show a full view of all orders you have.

    - `list f/cake` : Lists all orders with "cake" in the food's name. Good for finding orders of a similar type, or
      sharing the same customer.

    - `add f/Birthday Cake c/John Doe p/1234 e/johnd@example.com a/John street, block 123, #01-01 d/30-03-2026` :<br>
      Adds an order named `Birthday Cake` with customer name `John Doe` to HomeChef. Price is taken from the menu
      automatically.<br>
      The newly added order should look like this:<br>
      ![sample order](images/sampleOrder.png)<br>
      Note that the ID number may defer if there are other orders in the list.<br>
      The date may also be of a different colour (red or orange) if the current date is after 30-03-2026.

    - `complete 1` : Marks the 1st order shown in the current list as completed. Helps to show what orders you have done
      at a glance!

    - `delete 3` : Deletes the 3rd order shown in the current list. Perfect for removing long completed orders that you
      won't refer to anymore.

    - `add-menu n/Potato Wedges $/2.20` : Adds a food item called `Potato Wedges` with a price of `$2.20` into the menu
      on the right.<br>
      The newly added menu item should look like this:<br>
      ![menu item](images/sampleMenuItem.png)<br>

    - `exit` : Exits the app. See you next time!

1.  Do refer to the [Features](#features) below for details of each command.

---

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add f/FOOD`, `FOOD` is a parameter which can be used as `add f/Chocolate Cake`.

- `INDEX` values can only be non-zero positive whole numbers. Any input `INDEX` that is `0`, **negative** or a **decimal
  ** will give an error message.<br>
  e.g. `0`, `-1` and `2.0` will give `Invalid command format` error messages.

- `INDEX` values cannot be larger than the size of the shown list.<br>
  e.g. With an order list of size `3`, inputting `INDEX` as `4` or **more** will give an error message saying
  `The order index provided is invalid`.

- `INDEX` values refer to the index number shown in the displayed order list.<br>
  e.g. A list may originally be of size 10, so `5` is a valid `INDEX`. Using `list f/Cake` shortens the displayed list
  to a size of 3. This makes `5` an invalid `INDEX` now.

- Items in square brackets are optional.<br>
  e.g `f/FOOD [t/TAG]` can be used as `f/Butter Cake t/no dairy` or as `f/Butter Cake`.

- Any items **not** in the square brackets are thus mandatory.<br>
  If any of the mandatory fields are missing, an `Invalid command format` error message will be shown.<br>
  e.g. `f/FOOD [t/TAG]` will give an error message if only `t/no dairy` is given.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/no peanuts`, `t/gluten-free t/extra sprinkles` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `f/FOOD p/PHONE`, `p/PHONE f/FOOD` is also acceptable.

- Parameters **only** accept alphabets and numbers as characters, and a few other special characters.<br>
  These special characters are: `(`, `)`, `[`, `]`, `-`, ` ` (a blank space).<br>
  However, the blank space **cannot** be used as the very first character of any parameter.

- Extra parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

## Order List commands:

The order list is the list on the left, showing all the orders made by customers for certain food items.

![order list](images/UiOrderListHighlight.png)

The following are the commands that interact with this order list.

### Adding an order: `add`

Adds an order to the order list.
All orders are initially set as 'Pending' and 'Unpaid'.

Format: `add f/FOOD c/NAME p/PHONE e/EMAIL a/ADDRESS d/DATE [q/QUANTITY] [t/TAG]…
[m/PAYMENT METHOD] [r/PAYMENT REF] [b/BANK NAME] [w/WALLET PROVIDER]`

- Orders have their completion status set to `Pending` by default.
- Orders also have their payment status set to `Unpaid` by default.
- Orders have their dates coloured according to the urgency of the Order.

> White indicates that the `Order` is not late, it is due **_more than 3 days_** from today's date.<br>
> ![normal date](images/normalDate.png)<br>
> Orange indicates that the `Order` is not late, but it is **_due within 3 days_** of today's date.<br>
> ![urgent date](images/urgentDate.png)<br>
> Red indicates that the `Order` is late, it was due **_before_** today's date.<br>
> ![overdue date](images/overdueDate.png)

<div markdown="span" class="alert alert-primary">:bulb:
**Notes about the add command:**<br>
* `FOOD` must match an **existing food's name** in the current menu exactly.
  * Giving an input that is not in the menu will show an error message telling you to `Use 'add-menu' to add it to the menu first.`
* The order's price is automatically taken from the matching menu item. Use `add-menu` or `edit-menu` to update a food's price.
* `QUANTITY` specifies how many units of the food item are ordered.
  * If omitted, `QUANTITY` defaults to `1`.
  * The total order price is calculated as `PRICE` x `QUANTITY`.
  * `QUANTITY` must be a positive integer between `1` and `999` (inclusive). Any other value will show an error message.
* An order can have any number of dietTags (including 0)
</div>

Examples:

- `add f/Red Bean Bun c/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/30-03-2026`
  `add f/Hawaiian Pizza c/Betsy Crowe t/Halal e/betsycrowe@example.com a/Newgate Prison p/1234567 d/12-12-2026 t/No peanuts`

- `add f/Bananas c/Monkey p/80801414 t/An actual monkey e/ooaa@ananab.com a/Monkey Village m/Bank r/123456789 b/Monkey Bank d/18-03-2026`
- `add f/Nasi Lemak q/3 c/John p/91234567 e/john@example.com a/123 Street d/01-12-2024` Adds an order of `3` units of
  `Nasi Lemak`. The total price shown will be the menu price multiplied by `3`.

### Listing all orders : `list`

Shows a list of all orders in the order list when no parameters are given,
Otherwise, shows a filtered list of orders that match the keywords given as parameters.
This can be useful for finding orders specific to a certain customer, a certain address or even of a certain food name.

Format: `list [d/DATE] [c/CUSTOMER] [f/FOOD] [p/PHONE] [cs/COMPLETION STATUS] [ps/PAYMENT STATUS]`

<div markdown="span" class="alert alert-primary">:bulb: **Notes about the list command:**<br>
* Lists all orders when no parameters are given.
* Filters are case-insensitive for `c/`, `f/` and `p/`.
* `DATE` must be in the format `dd-MM-yyyy`.
* `COMPLETION_STATUS` must be one of `Pending`, `In progress` or `Completed`.
* `PAYMENT_STATUS` must be one of `Paid`, `Unpaid` or `Partial`.
* Using `list` with no parameters is a good way to reset the order list view to show every order stored.
* A common `list` command is `list cs/Pending ps/Paid` to easily find orders that should be started on, since they have already been paid.
* Another common command is `list cs/Completed ps/Unpaid` to find orders that have been completed but not yet paid, to track troublesome customers who have yet to pay for their food.
</div>

Examples:

- `list` Diplays the full order list.
- `list d/18-10-2026` Displays an order list with all orders which have the date `18-10-2026`.
- `list p/1234` Displays an order list with all orders with phone numbers that have `1234` in them.
- `list d/16-04-2003 c/alice f/cake p/1234` Displays an order list with all orders that have the date `16-04-2003`, have
  `alice` in the customer's name, have `cake` in the food's name and have `1234` in the phone number.
- `list cs/Completed ps/Paid` Displays an order list with all orders that are `Completed` and `Paid`.<br>
  ![result for 'list cs/Completed ps/Paid'](images/listCompletedPaidResult.png)

### Marking an order as in progress: `inprogress`

Sets the completion status of an order to 'In progress'.
In progress orders have their completion status coloured orange.
This helps to easily tell at a glance when an order is currently in progress.

- On an in progress order, the completion status will not be changed.

Format: `inprogress INDEX`

### Marking an order as complete: `complete`

Sets the completion status of an order to 'Completed'.
Completed orders have their completion status coloured green.
This helps to easily tell at a glance when an order is completed.

- On a completed order, the completion status will not be changed.

Format: `complete INDEX`

### Marking an order as pending: `pending`

Sets the completion status of an order to 'Pending'.
Pending orders have their completion status coloured dark grey.
This helps to easily tell at a glance when an order has yet to be worked on.

- On a pending order, the completion status will not be changed.

Format: `pending INDEX`

### Marking an order as paid: `paid`

Sets the payment status of an order to 'Paid'.
Paid orders have their payment status coloured green.
This helps to easily tell at a glance when an order has been totally paid for by a customer.

- On a paid order, the completion status will not be changed.

Format: `paid INDEX`

### Marking an order as partially paid: `partial`

Sets the payment status of an order to 'Partial'.
Partially paid orders have their payment status coloured yellow.
This helps to easily tell at a glance when an order has been partially paid for by a customer.

- On a partially paid order, the completion status will not be changed.

Format: `partial INDEX`

### Marking an order as unpaid: `unpaid`

Sets the payment status of an order to 'Unpaid'.
Unpaid orders have their payment status coloured red.
This helps to easily tell at a glance when an order has yet to be paid by a customer.

- On an unpaid order, the completion status will not be changed.

Format: `unpaid INDEX`

### Generating a receipt: `receipt`

Generates a plain-text receipt file for the specified order.

Format: `receipt INDEX`

- A receipt file is created in a `receipts` folder beside the HomeChef data file.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can also use the shortcut command `rec`.
</div>

Examples:

- `receipt 1` Prints a receipt for the order located at `INDEX` 1 of the shown list.
- `rec 2` Prints a receipt for the order located at `INDEX` 2 of the shown list.

### Editing an order : `edit`

Edits an existing order in the order list.
This helps with updating orders when information changes, without having to delete and re-add the order to the list.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
* Completion status and payment status cannot be modified using the `edit` command and **must** be modified using the above commands.
</div>

Format:
`edit INDEX [f/FOOD] [c/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DATE] [q/QUANTITY] [t/TAG]…
[m/PAYMENT METHOD] [r/PAYMENT REF] [b/BANK NAME] [w/WALLET PROVIDER]`

<div markdown="span" class="alert alert-primary">:bulb:
**Notes about the edit command:**<br>
* At least one of the optional fields must be provided.
  * If no fields are provided, a message will appear telling you to provide a field.
* Existing values will be updated to the input values.
* If `f/FOOD` is changed, the order’s price is automatically updated to match the new menu item’s price.
* If `q/QUANTITY` is changed, the order’s total price is recalculated as `unit price × quantity`.
* When editing dietTags, the existing dietTags of the order will be removed i.e adding of dietTags is not cumulative.
* You can remove all the order’s dietTags by typing `t/` without
    specifying any dietTags after it.
</div>

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st order to be `91234567`
  and `johndoe@example.com` respectively.
- `edit 2 c/Betsy Crower t/` Edits the name of the 2nd order's customer to be `Betsy Crower` and clears all existing
  dietTags.
- `edit 1` Shows an error message saying `At least one field to edit must be provided.`

### Deleting an order : `delete`

Deletes the specified order.

Format: `delete INDEX`

- Deletes the order at the specified `INDEX`.
- The index refers to the index number shown in the displayed order list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd order in the current list.
- `list c/Betsy` followed by `delete 1` deletes the 1st order in the filtered results.

### Clearing all entries : `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This action **cannot be reversed** so only do this if you are sure you want to delete **every** order in the list. <br> If not, use the delete command instead.
</div>

Clears all entries from the order list.

Format: `clear`

## Menu commands:

The menu is the list on the right, indicating the food items you have for sale.

![menu](images/UiMenuHighlight.png)

The following are the commands that interact with this menu.

<div markdown="block" class="alert alert-info">
**:information_source: Notes about the menu:**<br>

- Any modifications to the menu will not affect existing orders.
  - For example: There is an order with a food name `Birthday Cake`. Deleting or editing `Birthday Cake` in the **menu
    ** will not affect this existing order.<br>
    But **future orders** will not be able to add food called `Birthday Cake` as it now does not exist in the menu.
  - This is so that you can freely change the menu without affecting past orders. After all, if someone ordered bread
    but one year later you switched to cooking noodles, that old order should still be retained for recording
    purposes!

</div>

### Adding a food item : `add-menu`

Adds a food item of the given name, price and availability to the menu.

Format: `add-menu n/NAME $/PRICE [v/AVAILABILITY]`

<div markdown="block" class="alert alert-info">
**:information_source: Notes about the add-menu command:**<br>
* `NAME` must be unique, meaning no 2 food items in the menu can share the exact same name. This is **not** case-sensitive, so `birthday cake` and `Birthday Cake` are considered duplicates.
* `PRICE` is a non-negative number up to 2 decimal places. Having less than 2 decimals is accepted.
  * Giving an input that is **not a number** or a number with **more than 2 decimals** will cause an error message to appear telling you the correct format you should use.
* Similar functionality to that of `add` for the order list, except the fields have different prefixes.
* `AVAILABILITY` only accepts `true` or `false` spelled exactly.
  * Typing anything else will give an error message stating `Availability must be 'true' or 'false'`.
* If not specified, `AVAILABILITY` will be set as `Available`.
</div>

Examples:

- `add-menu n/Bee Hoon $/5` Adds a food item called `Bee Hoon` into the menu with a price of `$5` and is specified as
  `Available`.
- `add-menu n/Mee Goreng $/6.00 v/false` Adds a food item called `Mee Goreng` into the menu with a price of `$6.00` and
  is specified is `Unavailable`.

### Deleting a food item : `delete-menu`

Deletes the food item identified by the index number used in the displayed menu list.

Format: `delete-menu INDEX`

<div markdown="block" class="alert alert-info">
**:information_source: Notes about the edit-menu command:**<br>
* You **cannot** delete a menu item that has a food item that is already in use in an order.
</div>

### Editing a food item : `edit-menu`

Edits an existing food item in the menu.
Similar functionality to that of `edit` for the order list, except the fields have different prefixes.

Format: `edit-menu INDEX [n/NAME] [$/PRICE] [v/AVAILABILITY]`

<div markdown="block" class="alert alert-info">
**:information_source: Notes about the edit-menu command:**<br>
* `AVAILABILITY` only accepts `true` or `false` spelled exactly.
  * Typing anything else will give an error message stating `Availability must be 'true' or 'false'`.
* Editing the `PRICE` of a menu item **will not** change the price of existing orders. This is because old orders may have prices that differ from the new price of a menu item, for book keeping purposes.
</div>

Example:

- `edit-menu 1 n/Raisin Cookies $/2.00` Edits the food in the first position of the displayed menu to have the name
  `Raisin Cookies` and a price of `$2.00`.
- `edit-menu 2 n/Pain au Chocolat $/3.50 v/false` Edits the food in the second position of the displayed menu to have
  the name `Pain au Chocolat` and a price of `$3.50`.

## Other commands:

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

- You can also exit the program by using your mouse cursor and clicking on the dropdown tab labelled `File` and then
  clicking on `Exit`.<br>

### Saving the data

HomeChef data is saved in the hard disk automatically after any command that changes the data. There is no need to save
manually.<br>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
It is, however, recommended that a backup of the homechef.json and menu.json files are made by copying them to a separate folder outside of the Homechef folder. This will allow you to copy the files back to the `data` folder when needed, such as when a `clear` command is accidentally executed.
</div>

### Editing the data file

HomeChef data is saved automatically as a JSON file `[JAR file location]/data/homechef.json`. Advanced users are welcome
to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file make its format invalid, HomeChef will **discard all data** and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the HomeChef to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

# FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Download the app in the other computer and set it up as mentioned in the Quick Guide section above.<br>
Overwrite the empty data file it creates with the file that contains the data of your previous HomeChef home folder (the
`homechef.json` and `menu.json` files).

**Q**: How do I get back the sample data that the app came with when I first booted it up?<br>
**A**: Open the folder that contains `homechef.jar`. Simply delete the `homechef.json` and `menu.json` files located in
the `data` folder. The next time you open the app, all the original sample orders and menu items will be restored.

**Q**: What's the rectangular box below where I put in the commands?<br>
**A**: That's the status window! It tells you if the commands you type in are typed correctly and if it is executed
properly. It also gives suggestions and hints if you input commands incorrectly.<br>
If the status information given is still unclear, feel free to refer to the command information above.

**Q**: The order list is blank! Is my data all gone?<br>
**A**: This may not necessarily be the case. Check by using the `list` command with **no parameters**. This should reset
the order list to its default view, which includes every single order that has been added. If this still fails to
resolve the problem, see the next question.

**Q**: Help! My data **all** been deleted!<br>
**A**: Unfortunately, **yes**. There is no way to recover the data unless a **backup copy** was made of the
`homechef.json` and `menu.json` files.<br>
If the copies exist, copy them over to the `data` folder located in the folder that contains the jar file you
downloaded.

# Command summary

| Action               | Format, Examples                                                                                                                                                                                                                                                    |
| -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Add**              | `add f/FOOD c/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE [q/QUANTITY] [t/TAG]…​ [m/PAYMENT METHOD] [r/PAYMENT REF] [b/BANK NAME] [w/WALLET PROVIDER]` <br> e.g., `add f/Chicken Rice c/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd d/30-03-2026` |
| **List**             | `list [d/DATE] [c/CUSTOMER] [f/FOOD] [p/PHONE] [cs/COMPLETION_STATUS] [ps/PAYMENT_STATUS]`<br> e.g., `list d/18-10-2026 cs/completed ps/Paid`                                                                                                                       |
| **Mark In Progress** | `inprogress INDEX` <br> e.g., `inprogress 2`                                                                                                                                                                                                                        |
| **Mark Complete**    | `complete INDEX` <br> e.g., `complete 4`                                                                                                                                                                                                                            |
| **Mark Pending**     | `pending INDEX` <br> e.g., `pending 3`                                                                                                                                                                                                                              |
| **Mark Paid**        | `paid INDEX` <br> e.g., `paid 1`                                                                                                                                                                                                                                    |
| **Mark Partial**     | `partial INDEX` <br> e.g., `partial 1`                                                                                                                                                                                                                              |
| **Mark Unpaid**      | `unpaid INDEX` <br> e.g., `unpaid 1`                                                                                                                                                                                                                                |
| **Edit**             | `edit INDEX [f/FOOD] [c/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DATE] [q/QUANTITY] [t/TAG]…​ [m/PAYMENT METHOD] [r/PAYMENT REF] [b/BANK NAME] [w/WALLET PROVIDER]`<br> e.g.,`edit 2 c/James Lee e/jameslee@example.com`                                      |
| **Delete**           | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                                                                 |
| **Clear**            | `clear`                                                                                                                                                                                                                                                             |
| **Add Menu**         | `add-menu n/NAME $/PRICE [v/AVAILABILITY]` <br> e.g., `add-menu n/Bee Hoon $/5.00 v/true`                                                                                                                                                                           |
| **Delete Menu**      | `delete-menu INDEX`<br> e.g., `delete 3`                                                                                                                                                                                                                            |
| **Edit Menu**        | `edit-menu INDEX [n/NAME] [$/PRICE] [v/AVAILABILITY]` <br> e.g., `edit-menu 2 n/Pain au Chocolat $/3.50 v/true`                                                                                                                                                     |
| **Help**             | `help`                                                                                                                                                                                                                                                              |
| **Exit**             | `exit`                                                                                                                                                                                                                                                              |
