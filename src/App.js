import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import Navbar from './components/Website/Navbar/Navbar';
import Home from './components/Website/Home/Home';
import Footer from './components/Website/Footer/Footer';
import Login from './components/Website/Login/Login';
// import Homepage from './components/Dashboard/Homepage';
// import Sidebar from './components/Dashboard/SideBar';

import { ProSidebarProvider } from 'react-pro-sidebar';
import DashboardNavbar from './components/Dashboard/DashboardNav/DashboardNavbar';
import MyProfile from './components/Dashboard/AccountInfo/MyProfile/MyProfile';
import MainLayout from './components/Dashboard/MainLayout/MainLayout';
import Header from './components/Dashboard/Header/Header';
import MyClients from './components/Dashboard/AccountInfo/MyClients/MyClients';
import MyCreditTransfers from './components/Dashboard/AccountInfo/MyCreditTransfers/MyCreditTransfers';
import MyTransactions from './components/Dashboard/AccountInfo/MyTransactions/MyTransactions';
import Send from './components/Dashboard/Messaging/Send/Send'
function App() {
  return (

    // <BrowserRouter>
    //     <Navbar/>

    // <Routes>
    //   {/* before login */}
    //     <Route index element={<Home/>}/>
    //     <Route path='overview' element={<Home/>}/>
    //     <Route path='httpapi' element={<Home/>}/>
    //     <Route path='smppaccess' element={<Home/>}/>
    //     <Route path='pricing' element={<Home/>}/>
    //     <Route path='login' element={<Login/>}/>
    //     <Route path='createaccount' element={<Home/>}/>

    //     {/* after login */}

    //     {/* <Route path='homepage' element={<Homepage/>}/> */}
    // </Routes>
    // <Footer/>

    // </BrowserRouter>
    <div id='app'>
          <BrowserRouter>
      <Header />
      <div className='all' style={{ display: 'flex', alignItems: 'flex-start' }}>
        <div className='sidebar-menu'>
          <DashboardNavbar style={{ position: 'relative', top: '0px' }} />
        </div>
        <div className='page-contents'>
          <Routes>
            <Route path='/my-profile' element={<MyProfile />}/>
            <Route path='/my-clients' element={<MyClients/>}/>
            <Route path='/my-credit-transfers' element={<MyCreditTransfers/>}/>
            <Route path='/my-transactions' element={<MyTransactions/>}/>
            <Route path='/send' element={<Send/>}/>
            {/* <Route index element={<MainLayout/>}/> */}
          </Routes>
        </div>



        {/* <Outlet/> */}
      </div>
    </BrowserRouter>
    </div>

  );
}

export default App;
