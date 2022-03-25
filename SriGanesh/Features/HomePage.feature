
Feature:FicusRealTime-HomepageVerification
  
  Scenario Outline: Top Ribbon verification
    Given I am on Ficusrealtime homepage
    Then i should see title as Ficus Realtime
    And on the top right side i should see "<mobilenumber>" and "<email>"
    And I should see ClientLogin Button along with facebook twitter and google links
    And I should see "<alltabs>"
    And I should see imageslider with "<specifiedslidercount>"
    And I should see Why KeaGMP link which shows text when clicked
    And I should see "<specifiedheadings>" of services
  Examples:
  |mobilenumber|email|alltabs|specifiedslidercount|specifiedheadings|
  |9966400511|info@ficusrealtime.com|Home,About Us,KeaGMP,Features,Modules,News,Contact Us|2|Web-Based Solution,Mobile Friendly,Zero Effort Ecosystem,Safe & Secure,Convenient & Competent,Innovative & Collaborative|
  