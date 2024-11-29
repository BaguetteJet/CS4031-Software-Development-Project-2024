# CS4031-Software-Development-Project-2024

## Submission Details:
- UML diagram, CRC cards, Contributions and HELP file in `/Supporting Documentation`
- JAVADOC can be accessed in `/JAVADOC Documentation/index.html`
- All test data files in `/data`
- `RunSystem.java` is the main java file
  
Follow insturctions in `/Supporting Documentation/HELP_INSTRUCTIONS.txt` for a sample execution  

## It is assumed the system runs at least ONCE a day
The system is intended to run once a day, skipping days may result is issues and date mismatch!  
Key dates include: every 2nd Friday of a month, every 25th of a month and every 25th of October of a year.
  
## Group 4 Members:
Igor Kochanski - 23358459  
Ciaran Whelan - 23370211  
Luke Scanlon - 23390573  

## Key Ideas

- build a Payroll system for UL Staff in JAVA [salary info](https://www.ul.ie/hr/current-staff/pay-benefits/salary-information)
- similar salary scales for hourly paid employees
  
- part-time employees submit pay claim form by 2nd Friday of month
- generate pay slips for all full-time staff and hourly paid staff (with current claims) on the 25th day of each month

- certain deductions must be made (See www.revenue.ie for more information)
  
- In October each year, full-time staff are moved to the next point on their salary scale
- employee may be promoted to the next salary scale within their professional category

- employees, payrolls, payslip details including historic payslips, stored in CSV files

- three user types
  1. employee user can log in, see their details, and view their most recent or historical payslips
  2. admin user can log in and can add a new employee to the system
  3. human resources user can log in, implement the promotion functionality for full-time staff
     - staff member should be asked to confirm the promotional changes being applied
       
- A command line interface (CLI) to allow a graphical user interface (GUI) to be substituted easily

## Must Submit:

1. A document outlining the Class Responsibility Collaboration (CRC) cards. (Word Document)
2. A UML class diagram showing the relationships between the classes. This should include the class names (no details of attributes/constructors/methods required). It must show the relationships between the classes (class inheritance, interface inheritance, aggregation, dependency as appropriate) and utilising the notation presented in lecture slides (please refer to the slides). This UML diagram must be generated manually and drawn using a tool like draw.io. (PNG file of Diagram)
3. Documentation for the software generated using the javadoc utility and a help file describing briefly how to run the application.
4. The source code for the system where each Java class/interface is stored in a separate file.
5. Any csv files that are required by the system should also be included. These files must be populated with data (e.g. sample data used to test the application).
6. The completed contributions file which will be available on the Brightspace Content Project tab.
   
- The use of a Github repository for source code version management.

IF YOU DON'T KNOW YET:
"A pay slip refers to the document an employee receives in each pay period that outlines their earnings, deductions, and net pay. On the other hand, payroll refers to the entire process of calculating and distributing employee compensation, including salaries, wages, bonuses, and benefits."

## Rules:

1. Strictly groups with max size 4 [groups](https://ulcampus-my.sharepoint.com/:x:/g/personal/michael_english_ul_ie/ES72TCVV7FZKnjaEl0boYqcBprC5rJiORFkOz4JYiHMeoA?e=5789Qa)
2. Interview where each team member will be requested to give a code/project walkthrough
    - (start of week 13 to the end of the examination period)
    - Failure to satisfactorily demonstrate your project will result in an F 
3. DO NOT submit any part of your work as part of their project
4. The use of GenAI tools will also be considered plagiarism
5. An F-grade is awarded if the contributions file is not included and complete
6. **Only one team member should submit the project**
7. Lab interviews may be undertaken before the deadline
8. A penalty of 10% for each day late submission

## DEADLINE: 12:00 02/12/24 Monday Week 13
![https://media3.giphy.com/media/aUovxH8Vf9qDu/giphy.gif](https://i.countdownmail.com/3oi3kv.gif)

# :)


