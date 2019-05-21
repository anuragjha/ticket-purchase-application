Ticket Purchase application - Service-Oriented Architecture
===========================================================

#### Final Code and Deployment Due - During interactive grading appointment scheduled during (or before) finals week. Details will be posted on slack.

#### Checkpoint Due - December 5, 2018 - All students will be required to demonstrate their progress on or before the last day of class - December 5, 2018. Students who fail to demonstrate reasonable progress before this checkpoint will not receive credit for the Project Checkpoint criterion.

For this project you will implement a service-oriented version of a ticket purchase application (i.e., your own EventBrite!). 

### Overview

The architecture of the service will be as follows:

![architecture](https://docs.google.com/drawings/d/e/2PACX-1vTjBg_ZETz31hzGUrNL6Fh6GoSEUA9iWLSwyLnPdY0Ixg0YuHhVliwo4fJvfUhFp8mXIxz1dOHMZHw1/pub?w=960&h=720)

**Web Front End** - The web front end will implement an external web service API for the application and will support APIs for the following operations:

1. Get a list of all events
2. Create a new event
3. Get details about a specific event
4. Purchase tickets for an event
5. Create a user
6. See a user's information, including *details* of all events for which the user has purchased tickets
7. Transfer tickets from one user to another

**Event Service** - The event service will manage the list of events and the number of tickets sold and available for each. When a ticket is purchased it is the responsibility of the Event Service to notify the User Service of the user's purchase. The API will support the following operations:

1. Create a new event
2. Get a list of all events
3. Get details about a specific event
4. Purchase tickets for an event, updating the user's ticket list

**User Service** - The user service will manage the user account information, including the events for which a user has purchased tickets. The API will support the following operations:

1. Create a new user
2. Get user details
3. Add a new ticket for a user
4. Transfer tickets from one user to another

### API

#### Front End Service

<details>
<summary>GET /events</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event Details<br/>
<pre>
[
	{
		"eventid": 0, 
		"eventname": "string", 
		"userid": 0,		
		"avail": 0, 
		"purchased": 0
	} 
]
	</pre></td></tr>
	<tr><td>400</td><td>No events found</td></tr>
</table>
</details>


<details>
<summary>POST /events/create </summary>
	
Body:

<pre>
{
	"userid": 0,
	"eventname": "string",
	"numtickets": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event created
<pre>
{
	"eventid": 0
}	
</pre></td></tr>
	<tr><td>400</td><td>Event unsuccessfully created</td></tr>
</table>
</details>

<details>
<summary>GET /events/{eventid}</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event Details<br/>
<pre>
{
	"eventid": 0, 
	"eventname": "string", 
	"userid": 0,		
	"avail": 0, 
	"purchased": 0
}
</pre></td></tr>
	<tr><td>400</td><td>Event not found</td></tr>
</table>
</details>

<details>
<summary>POST /events/{eventid}/purchase/{userid}</summary>
Body:

<pre>
{
	"tickets": 0
}
</pre>


Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Tickets purchased</td></tr>
	<tr><td>400</td><td>Tickets could not be purchased</td></tr>
</table>
</details>

<details>
<summary>POST /users/create</summary>

Body:

<pre>
{
	"username": "string"
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>User created<br/>
<pre>
{
	"userid": 0
}	
</pre></td></tr>
	<tr><td>400</td><td>User could not be created</td></tr>
</table>
</details>

<details>
<summary>GET /users/{userid}</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>User Details<br/>
<pre>
{
	"userid": 0,
	"username": "string",
	"tickets": [
		{
			"eventid": 0, 
			"eventname": "string", 
			"userid": 0,		
			"avail": 0, 
			"purchased": 0
		}
	]	
}
</pre></td></tr>
	<tr><td>400</td><td>User not found</td></tr>
</table>
</details>

<details>
<summary>POST /users/{userid}/tickets/transfer</summary>

Body:
<pre>
{
	"eventid": 0,
	"tickets": 0,
	"targetuser": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event tickets transferred</td></tr>
	<tr><td>400</td><td>Tickets could not be transferred</td></tr>
</table>

</details>


#### Event Service

<details>
<summary>POST /create</summary>

Body:

<pre>
{
	"userid": 0,
	"eventname": "string",
	"numtickets": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event created
<pre>
{
	"eventid": 0
}	
</pre></td></tr>
	<tr><td>400</td><td>Event unsuccessfully created</td></tr>

</table>
</details>

<details>
<summary>GET /list</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>List of events <br/>
<pre>
[
	{
		"eventid": 0, 
		"eventname": "string", 
		"userid": 0,		
		"avail": 0, 
		"purchased": 0
	}
]	
</pre>
	</td></tr>
</table>
</details>

<details>
<summary>GET /{eventid}</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event details<br/>
<pre>
{
	"eventid": 0, 
	"eventname": "string", 
	"userid": 0,		
	"avail": 0, 
	"purchased": 0
}
</pre>
	</tr>
	<tr><td>400</td><td>Event not found</tr>
</table>
</details>

<details>
<summary>POST /purchase/{eventid}</summary>

Body:

<pre>
{
	"userid": 0,
	"eventid": 0,
	"tickets": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event tickets purchased</tr>
	<tr><td>400</td><td>Tickets could not be purchased</tr>
</table>

</details>


#### User Service

<details>
<summary>POST /create</summary>

Body:

<pre>
{
	"username": "string"
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>User created<br/>
<pre>
{
	"userid": 0
}	
</pre>
</tr>
<tr><td>400</td><td>User unsuccessfully created</tr>
</table>
</details>

<details>
<summary>GET /{userid}</summary>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>User details<br/>
<pre>
{
	"userid": 0,
	"username": "string",
	"tickets": [
		{
			"eventid": 0
		}
	]
}
</pre>
</tr>
	<tr><td>400</td><td>User not found</tr>
</table>
</details>

<details>
<summary>POST /{userid}/tickets/add</summary>

Body:

<pre>
{
	"eventid": 0,
	"tickets": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event tickets added</tr>
	<tr><td>400</td><td>Tickets could not be added</tr>

</table>
</details>

<details>
<summary>POST /{userid}/tickets/transfer</summary>

Body:

<pre>
{
	"eventid": 0,
	"tickets": 0,
	"targetuser": 0
}
</pre>

Responses:

<table>
	<tr><td>Code</td><td>Description</td></tr>
	<tr><td>200</td><td>Event tickets transfered</tr>
	<tr><td>400</td><td>Tickets could not be transfered</tr>
</table>

</details>
