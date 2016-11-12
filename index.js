/**
* @Author: 杜绍彬 <dushaobin>
* @Date:   2016-01-15T02:10:13+08:00
* @Email:  dushaobin@we.com
* @Last modified by:   dushaobin
* @Last modified time: 2016-10-11T17:19:20+08:00
* @License: MIT
*/

import {
    PropTypes
} from 'react';

var { View, requireNativeComponent } = require('react-native');

var iface = {
  name: 'BaiduMap',
  propTypes: {
    ...View.propTypes,
    mode: PropTypes.number,
    trafficEnabled: PropTypes.bool,
    heatMapEnabled: PropTypes.bool,
    marker:PropTypes.array,
    level:PropTypes.number,
    center:PropTypes.array,
    zoomBtnVisibility:PropTypes.bool,
    markerClickEnabled:PropTypes.bool
  }
}

module.exports = requireNativeComponent('RCTBaiduMap', iface);
