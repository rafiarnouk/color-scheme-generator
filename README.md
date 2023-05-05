# Rafi's Personal Project
## Colour Scheme Generator

### What is it?
Inspired by my passion for graphic design, I will be creating a **colour scheme generator** for my personal project.
Colour schemes are an integral part of any design, whether it is a movie poster or a user interface. There are some key
ideas that must be present in a good colour scheme, such as contrast and value. I want to create a program that, given 
a colour of the user's choice, outputs a variety of colour schemes for the user to consider. The user can then look
through the schemes and if they are captivated by any of them, they can save it to their collection for further viewing.
If not, they can reconsider their starting colour and use the generator until they find a colour scheme that fits their
needs.

### Who will use it?
This program can be used by anyone who is designing something, no matter what level of colour theory they have. It can 
be used by graphic designers, game developers, fashion designers, etc. 

### Why this?
Graphic design has been a hobby of mine for over half a decade now, and colour schemes are crucial in the field.
With this program I will be able to automate the process of coming up with colour schemes. I am very curious to see what
level of complexity I can reach with my current skills in programming, so I am excited to see where this project goes.

## User Stories
- As a user, I want to be able to **add** a colour scheme to my collection.
- As a user, I want to be able to **generate** colour schemes for a colour that I have chosen.
- As a user, I want to be able to **select** from different types of colour schemes, such as monochromatic or analogous ones.
- As a user, I want to be able to **remove** a colour scheme from my collection.
- As a user, I want the option to **save** a gallery to a file.
- As a user, I want the option to **reload** a gallery from a file.

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking generate scheme
- You can generate the second required action related to adding Xs to a Y by brightening or darkening all schemes are deleting all schemes
- You can locate my visual component by viewing the gallery
- You can save the state of my application by pressing save gallery
- You can reload the state of my application by pressing load gallery

## Phase 4: Task 2
### Example Log
Wed Apr 05 00:56:26 PDT 2023 -
Added scheme Monochrome (107, 100, 75) to gallery

Wed Apr 05 00:56:26 PDT 2023 -
Displayed all schemes

Wed Apr 05 00:56:32 PDT 2023 -
Displayed all schemes

Wed Apr 05 00:56:34 PDT 2023 -
Loaded gallery from file

Wed Apr 05 00:56:36 PDT 2023 -
Displayed all schemes

Wed Apr 05 00:56:41 PDT 2023 -
Added scheme Analogous (107, 100, 171) to gallery

Wed Apr 05 00:56:41 PDT 2023 -
Displayed all schemes

Wed Apr 05 00:56:48 PDT 2023 -
Saved gallery to file

Wed Apr 05 00:56:49 PDT 2023 -
Displayed all schemes

Wed Apr 05 00:56:50 PDT 2023 -
Brightened all schemes 

Wed Apr 05 00:56:52 PDT 2023 -
Darkened all schemes

Wed Apr 05 00:56:53 PDT 2023 -
Removed all schemes from gallery

## Phase 4: Task 3
A flaw in my design that jumps out to me by looking at by my UML diagram is the association relationship between GUI
and scheme. It makes complete sense why GUI would have a gallery, but there is no real reason for it to have a colour scheme.
I think it was something I did in the moment to make my life easier, but in hindsight, there are more sensible ways to approach
writing this code. By removing this association, I would reduce the coupling in my project, making the project adhere more 
closely to design standards, making it more adaptable. Apart from that, I think my program structure is mostly straightforward
and concise. 