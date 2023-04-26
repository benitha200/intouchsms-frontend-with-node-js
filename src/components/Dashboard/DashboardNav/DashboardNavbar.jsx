import { Sidebar, Menu, MenuItem, SubMenu, useProSidebar } from 'react-pro-sidebar';
import './DashboardNav.css'
import { Link } from 'react-router-dom';
// import MyProfile from '../AccountInfo/MyProfile/MyProfile';
import { BsFillPersonFill } from 'react-icons/bs'
import { BsPeopleFill } from 'react-icons/bs'
import { BsArrowLeftRight } from 'react-icons/bs'
import { BsCreditCard2BackFill } from 'react-icons/bs'
import { BsCashCoin } from 'react-icons/bs'
import { BsPersonPlusFill } from 'react-icons/bs'
import { MdContactMail, MdForwardToInbox } from 'react-icons/md'
import { MdMarkEmailRead } from 'react-icons/md'
// import { MdEmail } from 'react-icons/md'
import { BsFillCalendar2WeekFill } from 'react-icons/bs'
import { MdOutlineMail } from 'react-icons/md'
// import { BsList } from 'react-icons/bs'
import { Button } from '@mui/material';
// import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
// import PeopleOutlinedIcon from "@mui/icons-material/PeopleOutlined";
// import MessageRoundedIcon from "@mui/icons-material/MessageRounded"
import PersonRoundedIcon from "@mui/icons-material/PersonRounded"
import MailRounded from "@mui/icons-material/MailRounded"
import ContactsOutlinedIcon from "@mui/icons-material/ContactsOutlined";
// import ReceiptOutlinedIcon from "@mui/icons-material/ReceiptOutlined";
// import CalendarTodayOutlinedIcon from "@mui/icons-material/CalendarTodayOutlined";
import MenuIcon from '@mui/icons-material/Menu';
import { useState } from 'react';
// import { FiHome, FiLogOut, FiArrowLeftCircle, FiArrowRightCircle } from "react-icons/fi";


const DashboardNavbar = props => {

  const { collapseSidebar } = useProSidebar();

  //menuCollapse state using useState hook
  const [menuCollapse, setMenuCollapse] = useState(false)
  //custom function that will change menucollapse state from false to true and true to false
  const menuIconClick = () => {
    //condition checking to change state from true to false and vice versa
    menuCollapse ? setMenuCollapse(false) : setMenuCollapse(true);
  };


  const [activePage, setActivePage] = useState(null);

  function handleActive(event) {
    if (!event.target.classList.value.includes()) {
      event.target.classList.toggle('active');
      if (activePage)
        activePage.classList.remove("active")
      setActivePage(event.target);
    }
  }
  return (
    <div>

      <div className='all'>
        <>

          <Sidebar style={{ height: "100vh" }}>
            <div className="burger-icon">
              <Button onClick={() => collapseSidebar()} sx={{ color: 'black' }} className=''><MenuIcon /></Button>

            </div>

            {/* <div className="closemenu" onClick={menuIconClick}>
              {/* changing menu collapse icon on click */}
              {/* {menuCollapse ? ( 
                <FiArrowRightCircle />
              ) : (
                <FiArrowLeftCircle />
              )}
            </div> */}


            <Menu>

              <SubMenu label="Account Info" className='sub-menu' component={<Link to="/account-info" />} icon={<PersonRoundedIcon />} sx={{ backgroundColor: 'grey' }} >
                <MenuItem component={<Link to="/account-info/" className='sidebar-links' />} icon={<BsFillPersonFill className='5' />} onClick={handleActive}>  My Profile </MenuItem>
                <MenuItem component={<Link to="/account-info/my-clients" className='sidebar-links' />} icon={<BsPeopleFill />} onClick={handleActive}> My Clients </MenuItem>
                <MenuItem component={<Link to="/account-info/my-credit-transfers" className='sidebar-links' />} icon={<BsCreditCard2BackFill />} onClick={handleActive}> My Credit Transfers </MenuItem>
                <MenuItem component={<Link to="/account-info/my-transactions" className='sidebar-links' />} icon={<BsArrowLeftRight />}> My Transactions </MenuItem>
                <MenuItem component={<Link to="/account-info/my-commissions" className='sidebar-links' />} icon={<BsCashCoin />}> My Commissions </MenuItem>
              </SubMenu>

              <SubMenu label="Address Book" className='sub-menu' icon={<ContactsOutlinedIcon />} component={<Link to='/address-book' />}>
                <MenuItem icon={<BsPersonPlusFill />} component={<Link to="/address-book/" />}> Contacts</MenuItem>
                <MenuItem icon={<BsPeopleFill />} component={<Link to="/address-book/groups" />}> Groups </MenuItem>
              </SubMenu>

              <SubMenu label="Messaging" className='sub-menu' icon={<MailRounded />} component={<Link to='/messaging' />}>
                <MenuItem component={<Link to="/messaging/" className='sidebar-links' />} icon={<MdForwardToInbox />} > Send</MenuItem>
                <MenuItem component={<Link to="/messaging/custom" className='sidebar-links' />} icon={<MdContactMail />}> Custom </MenuItem>
                <MenuItem icon={<BsFillCalendar2WeekFill />}> Scheduled</MenuItem>
                <MenuItem icon={<MdMarkEmailRead />}> Sent </MenuItem>
                <MenuItem icon={<MdOutlineMail />}> Inbox</MenuItem>
              </SubMenu>

              {/* <SubMenu label="CRM">

                <SubMenu label="Users">
                  <MenuItem> Customers</MenuItem>
                  <MenuItem> Agents </MenuItem>
                  <MenuItem> Resellers </MenuItem>
                </SubMenu>

                <SubMenu label="Sales">
                  <MenuItem> Packages</MenuItem>
                  <MenuItem> Topups </MenuItem>
                  <MenuItem> Credit Transfers </MenuItem>
                  <SubMenu label="Payments">
                    <MenuItem> Mobile Money</MenuItem>
                    <MenuItem> Cash </MenuItem>
                    <MenuItem> Cheque</MenuItem>
                    <MenuItem> Bank Transfer </MenuItem>
                  </SubMenu>
                  <MenuItem> Income</MenuItem>
                </SubMenu>

                <SubMenu label="Payables">
                  <MenuItem> Vendor</MenuItem>
                  <MenuItem> Commission</MenuItem>
                  <MenuItem>CashBack</MenuItem>
                </SubMenu>

                <SubMenu label="Transaction Logs">
                  <MenuItem> Customer</MenuItem>
                  <MenuItem> Agent</MenuItem>
                  <MenuItem>Income</MenuItem>
                  <MenuItem>Mobile Money</MenuItem>
                </SubMenu>


                <SubMenu label="Settings">

                  <SubMenu label="Billing Settings">
                    <MenuItem> SenderNames</MenuItem>
                  </SubMenu>

                </SubMenu>


              </SubMenu>

              <SubMenu label="SMS Manager">

                <SubMenu label="OutBound">
                  <MenuItem> HTTP</MenuItem>
                  <MenuItem> SMPP</MenuItem>
                  <MenuItem> Scheduled</MenuItem>
                </SubMenu>

                <SubMenu label="InBound">
                  <MenuItem> HTTP</MenuItem>
                </SubMenu>


                <SubMenu label="Transaction Logs">
                  <MenuItem> OutBound HTTP</MenuItem>
                  <MenuItem> OutBound SMPP</MenuItem>
                  <MenuItem> InBound HTTP</MenuItem>
                </SubMenu>

              </SubMenu>

              <SubMenu label="administration">
                <SubMenu label="Users">
                  <MenuItem>Customers</MenuItem>
                  <MenuItem>Agents</MenuItem>
                  <MenuItem>Resellers</MenuItem>
                </SubMenu>

                <MenuItem>Roles</MenuItem>

              </SubMenu> */}

            </Menu>
          </Sidebar>
        </>



      </div>


    </div>
  )
}

export default DashboardNavbar

