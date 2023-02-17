import { OutlinedFlag } from '@mui/icons-material'
import React from 'react'
import { Sidebar } from 'react-pro-sidebar'
import MyProfile from '../AccountInfo/MyProfile/MyProfile'
import DashboardNavbar from '../DashboardNav/DashboardNavbar'
import { Outlet } from 'react-router-dom'

const MainLayout = (props) => {
    return (
        <div>
            <div className='Header'>
                <h3>Intouchsms Communication Platform</h3>
            </div>
            <div className='main-layout' style={{ display: "flex" }}>
                <div className='left'>
                    <DashboardNavbar />
                </div>
                <div className='right'>
                <Outlet/>
                </div>

            </div>
        </div>
    )
}

export default MainLayout
